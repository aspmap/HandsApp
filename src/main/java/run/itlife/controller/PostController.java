package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.MultipartFile;
import run.itlife.dto.PostDto;
import run.itlife.entity.User;
import run.itlife.repository.UserRepository;
import run.itlife.service.*;
import run.itlife.utils.SaveFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static run.itlife.enums.FileExtensions.PNG;
import static run.itlife.enums.FileTypes.*;
import static run.itlife.messages.ErrorMessages.ERROR;
import static run.itlife.messages.ErrorMessages.NOT_PUBLISH_POST;

//Контроллер для постов (создание, редактирование, удаление)
@Controller
public class PostController {
    private static String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    private final PostService postService;
    private final LikesService likesService;
    private final UserService userService;
    private final CommentService commentService;
    private final SubscriptionsService subscriptionsService;
    private final UserRepository userRepository;
    private final ServletContext context;

    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    ServletContext servletContext;
    @Autowired
    private S3Service service;

    @Autowired
    public PostController(PostService postsService, LikesService likesService, UserService userService, CommentService commentService, ServletContext context, SubscriptionsService subscriptionsService, UserRepository userRepository) {
        this.postService = postsService;
        this.likesService = likesService;
        this.userService = userService;
        this.commentService = commentService;
        this.subscriptionsService = subscriptionsService;
        this.context = context;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String index(ModelMap modelMap, @RequestParam(required = false) String search) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts_sub", postService.findSubscribesPosts(username));
        modelMap.put("countPosts", postService.countSubscribesPosts(username));
        modelMap.put("isYourLike", postService.isLikePost(username)); // TODO как выдернуть id поста??
        return "posts/posts-detail-sub";
    }

    @GetMapping("/main")
    public String getLoginInfo(ModelMap modelMap, OAuth2AuthenticationToken authentication) {
        String username = authentication.getPrincipal().getAttribute("sub");
        User user = userRepository.findByUsername(username).orElseGet(() -> {
            User newUser = new User();
            newUser.setUsername(authentication.getPrincipal().getAttribute("sub"));
            newUser.setFirstname(authentication.getPrincipal().getAttribute("given_name"));
            newUser.setPassword(authentication.getPrincipal().getAttribute("at_hash"));
            newUser.setSurname(authentication.getPrincipal().getAttribute("family_name"));
            newUser.setEmail(authentication.getPrincipal().getAttribute("email"));
            newUser.setPhoto(authentication.getPrincipal().getAttribute("picture"));
            userService.createGoogleUser(newUser);
            return newUser;
        });

        setCommonParams(modelMap, username);
        modelMap.put("posts_sub", postService.findSubscribesPosts(username));
        modelMap.put("countPosts", postService.countSubscribesPosts(username));
        modelMap.put("isYourLike", postService.isLikePost(username));
        return "posts/posts-detail-sub";
    }

    //@RequestMapping(value = "/posts_detail", method = RequestMethod.GET)
    @GetMapping("/posts_detail") // такая же запись как и выше, но в другом виде, более современная
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String posts_detail(ModelMap modelMap) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.sortedPostsByDate(username));
        modelMap.put("countLikes", likesService.countLikesByUsername(username));
        return "posts/posts-detail";
    }

    @GetMapping("/posts")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String posts(ModelMap modelMap) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.sortedPostsByDate(username));
        modelMap.put("countPosts", postService.countPosts(username));
        modelMap.put("countSubscribe", subscriptionsService.countSubscribe(username));
        modelMap.put("countSubscribers", subscriptionsService.countSubscribers(username));
        return "posts/posts";
    }

    @GetMapping("/posts_detail_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String posts_detail_subuser(ModelMap modelMap, @PathVariable String user) {
        setCommonParams(modelMap);
        modelMap.put("posts", postService.sortedPostsByDate(user));
        modelMap.put("user_sub", user);
        modelMap.put("userinfo_sub", userService.findByUsername(user));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("isYourLike", postService.isLikePost(username));
        return "posts/posts-detail-subuser";
    }

    @GetMapping("/post/newVideo")
    @PreAuthorize("hasRole('USER')")
    public String postNewVideo(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "posts/post-new-video";
    }

    @PostMapping("/post/newVideo")
    @PreAuthorize("hasRole('USER')")
    public String postNewVideo(PostDto postDto, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        setCommonParams(modelMap);
        long postId;
        SaveFile sf = new SaveFile();
        Map<String, String> filenameMap = new HashMap<>();

        if (!file.isEmpty()) {
            try {
                if (file.getContentType().equals(VIDEO_MP4.getType()) || file.getContentType().equals(VIDEO_QT.getType())) {
                    filenameMap = sf.saveFile(username, context, file);
                    for (Map.Entry<String, String> entry : filenameMap.entrySet()) {
                        postDto.setExtFile(entry.getValue());
                        postDto.setPhoto(entry.getKey());
                    }
                    postId = postService.createPost(postDto);
                    return "redirect:/post/" + postId;
                } else {
                    return "messages-templates/error";
                }
            } catch (Exception e) {
                log.error(ERROR + e);
                return "messages-templates/error";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_POST);
            return "messages-templates/error";
        }
    }

    @GetMapping("/post/newImage")
    @PreAuthorize("hasRole('USER')")
    public String postNewImage(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "posts/post-new-img";
    }

    @PostMapping("/post/newImage")
    @PreAuthorize("hasRole('USER')")
    public String postNewImage(PostDto postDto, @RequestParam("file") String file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        setCommonParams(modelMap);
        long postId;
        SaveFile sf = new SaveFile();

        if (!file.isEmpty()) {
            try {
                String filename = sf.saveFile(username, context, file);
                postDto.setExtFile(PNG.getExtension());
                postDto.setPhoto(filename);
                postId = postService.createPost(postDto);
                return "redirect:/post/" + postId;
            } catch (Exception e) {
                log.error(ERROR + e);
                return "messages-templates/error";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_POST);
            return "messages-templates/error";
        }
    }

    @GetMapping("/post/newS3Image")
    @PreAuthorize("hasRole('USER')")
    public String postNewS3Image(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "posts/post-new-s3-img";
    }

    @PostMapping("/post/newS3Image")
    @PreAuthorize("hasRole('USER')")
    public String postNewS3Image(PostDto postDto, @RequestParam("file") String file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        setCommonParams(modelMap);
        long postId;
        SaveFile sf = new SaveFile();

        if (!file.isEmpty()) {
            try {
                File multipartFile = sf.saveS3File(username, context, file);
                //service.uploadS3File(username, multipartFile);
                postDto.setExtFile(PNG.getExtension());
                postDto.setStorageType("S3");
                postDto.setPhoto("https://storage.yandexcloud.net/handsapp/img/users/" + username);// + "/" + multipartFile.getName());
                postId = postService.createPost(postDto);
                return "redirect:/post/" + postId;
            } catch (Exception e) {
                log.error(ERROR + e);
                return "messages-templates/error";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_POST);
            return "messages-templates/error";
        }
    }

    @GetMapping("/post/{postId}/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(ModelMap modelMap, @PathVariable long postId) {
        setCommonParams(modelMap);
        postService.checkAuthority(postId);
        modelMap.put("post", postService.getAsDto(postId));
        return "posts/post-edit";
    }

    @PostMapping("/post/edit")
    @PreAuthorize("hasRole('USER')")
    public String postEdit(PostDto postDto, ModelMap modelMap) {
        // получаем имя юзера для формирования пути сохранения фото
        setCommonParams(modelMap);
        postService.checkAuthority(postDto.getPostId());
        postService.update(postDto);
        return "redirect:/post/" + postDto.getPostId();
    }

    @GetMapping("/post-view-sub/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String post_view_sub(@PathVariable long id, ModelMap modelMap) {
        setCommonParams(modelMap);
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentService.sortCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("countLikes", likesService.countLikesByPostId(id));
        modelMap.put("isLike", likesService.isLikePostForCurrentUser(id, username));
        return "posts/post-view-sub";
    }

    @GetMapping("/post/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String post(@PathVariable long id, ModelMap modelMap) {
        setCommonParams(modelMap);
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentService.sortCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("countLikes", likesService.countLikesByPostId(id));
        modelMap.put("isLike", likesService.isLikePostForCurrentUser(id, username));
        return "posts/post-view";
    }

    @PostMapping("/post/{id}/delete")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
        postService.delete(id);
    }

    @GetMapping("/post/{id}/delete_one_post")
    @PreAuthorize("hasRole('USER')")
    public String delete_one_post(@PathVariable long id) {
        postService.delete(id);
        return "redirect:/posts_detail";
    }

    @GetMapping("/posts_my_likes")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String posts_my_likes(ModelMap modelMap) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.selectMyLikesPosts(username));
        modelMap.put("countPosts", postService.countMyLikesPosts(username));
        return "posts/posts-my-likes";
    }

    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
        modelMap.put("contextPath", context.getContextPath());
    }

    private void setCommonParams(ModelMap modelMap, String username) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
        modelMap.put("contextPath", context.getContextPath());
    }

}