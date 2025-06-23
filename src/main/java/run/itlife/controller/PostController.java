package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.itlife.dto.PostDto;
import run.itlife.entity.User;
import run.itlife.repository.UserRepository;
import run.itlife.service.*;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;

import static run.itlife.enums.FileExtensions.*;
import static run.itlife.enums.FileTypes.*;
import static run.itlife.service.S3ServiceImpl.S3_ADDRESS;
import static run.itlife.utils.Properties.ErrorMessages.*;
import static run.itlife.utils.Properties.Files.*;
import static run.itlife.utils.Properties.Paths.*;

//Контроллер для постов (создание, редактирование, удаление)
@Controller
public class PostController {
    private final PostService postService;
    private final LikesService likesService;
    private final UserService userService;
    private final CommentService commentService;
    private final SubscriptionsService subscriptionsService;
    private final UserRepository userRepository;
    private final DialogsService dialogsService;
    private final ServletContext context;
    @Autowired
    CommonsParams commonsParams;
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    @Autowired
    ServletContext servletContext;
    @Autowired
    private S3Service service;

    @Autowired
    public PostController(PostService postsService, LikesService likesService, UserService userService, CommentService commentService, ServletContext context, SubscriptionsService subscriptionsService, UserRepository userRepository, DialogsService dialogsService) {
        this.postService = postsService;
        this.likesService = likesService;
        this.userService = userService;
        this.commentService = commentService;
        this.subscriptionsService = subscriptionsService;
        this.context = context;
        this.userRepository = userRepository;
        this.dialogsService = dialogsService;
    }

    @GetMapping("/main")
    public String findLoginInfo(ModelMap modelMap, OAuth2AuthenticationToken authentication) {
        String username = authentication.getPrincipal().getAttribute("sub");
        userRepository.findByUsername(username).orElseGet(() -> {
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

        commonsParams.setCommonParams(modelMap, username);
        modelMap.put("unreadMessagesTotal", dialogsService.findUnreadDialogs(username).size());
        modelMap.put("posts_sub", postService.findSubscribesPosts(username));
        modelMap.put("countPosts", postService.countSubscribesPosts(username));
        modelMap.put("isYourLike", postService.isLikePost(username));
        return "posts/posts-detail-sub";
    }

    //@RequestMapping(value = "/posts_detail", method = RequestMethod.GET)
    @GetMapping("/posts_detail") // такая же запись как и выше, но в другом виде, более современная
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPostsDetail(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.findSortedPostsByDate(username));
        modelMap.put("countLikes", likesService.countLikesByUsername(username));
        return "posts/posts-detail";
    }

    @GetMapping("/posts")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPosts(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.findSortedPostsByDate(username));
        modelMap.put("countPosts", postService.countPosts(username));
        modelMap.put("countSubscribe", subscriptionsService.countSubscribe(username));
        modelMap.put("countSubscribers", subscriptionsService.countSubscribers(username));
        return "posts/posts";
    }

    @GetMapping("/posts_detail_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPostsDetailSubuser(ModelMap modelMap, @PathVariable String user) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("posts", postService.findSortedPostsByDate(user));
        modelMap.put("isClosedProfile", userService.isClosedProfile(user));
        modelMap.put("user_sub", user);
        modelMap.put("userinfo_sub", userService.findByUsername(user));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("isYourLike", postService.isLikePost(username));
        return "posts/posts-detail-subuser";
    }

    @GetMapping("/post/new_video")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createNewPostVideo(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "posts/post-new-video";
    }

    @PostMapping("/post/new_video")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createNewPostVideo(PostDto postDto, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (file.getSize() > MAX_UPLOAD_VIDEO_FILE_SIZE_IN_MB) {
            commonsParams.setCommonParams(modelMap);
            return "messages-templates" + SEPARATOR + "errorVideoSize";
        }
        long postId;
        SaveFile sf = new SaveFile();

        if (!file.isEmpty()) {
            try {
                if (file.getContentType().equals(VIDEO_MP4.getType()) || file.getContentType().equals(VIDEO_QT.getType())) {
                    Map<String, String> filenameMap = sf.saveFile(username, context, file);
                    for (Map.Entry<String, String> entry : filenameMap.entrySet()) {
                        postDto.setExtFile(entry.getValue());
                        postDto.setPhoto(entry.getKey());
                    }
                    postId = postService.createPost(postDto);
                    return "redirect:/post_view/" + postId;
                } else {
                    commonsParams.setCommonParams(modelMap);
                    return "messages-templates/error";
                }
            } catch (Exception e) {
                log.error(ERROR + NOT_PUBLISH_POST);
                commonsParams.setCommonParams(modelMap);
                return "messages-templates/error";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_POST);
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/error";
        }
    }

    @GetMapping("/post/new_image")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createNewPostImage(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "posts/post-new-img";
    }

    @PostMapping("/post/new_image")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createNewPostImage(PostDto postDto, @RequestParam("file") String file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long postId;
        SaveFile sf = new SaveFile();

        if (!file.isEmpty()) {
            try {
                String filename = sf.saveFile(username, context, file);
                if (filename == null) {
                    commonsParams.setCommonParams(modelMap);
                    return "messages-templates" + SEPARATOR + "errorFileSize";
                }
                postDto.setExtFile(PNG.getExtension());
                postDto.setPhoto(filename);
                postId = postService.createPost(postDto);
                return "redirect:" + SEPARATOR + "post_view" + SEPARATOR + postId;
            } catch (Exception e) {
                log.error(ERROR + NOT_PUBLISH_POST);
                commonsParams.setCommonParams(modelMap);
                return "messages-templates/error";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_POST);
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/error";
        }
    }

    @GetMapping("/post/new_S3_image")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createNewPostS3Image(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "posts/post-new-s3-img";
    }

    @PostMapping("/post/new_S3_image")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createNewPostS3Image(PostDto postDto, @RequestParam("file") String file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long postId;
        SaveFile sf = new SaveFile();

        if (!file.isEmpty()) {
            try {
                File multipartFile = sf.saveS3File(file);
                if (!multipartFile.exists()) {
                    commonsParams.setCommonParams(modelMap);
                    return "messages-templates" + SEPARATOR + "errorS3FileSize";
                }
                if (multipartFile != null) {
                    service.uploadS3File(username, multipartFile);
                    postDto.setExtFile(PNG.getExtension());
                    postDto.setStorageType("S3");
                    postDto.setPhoto("https://" + S3_ADDRESS + SEPARATOR + "handsapp" + SEPARATOR + "img" + SEPARATOR + "users" + SEPARATOR + username + SEPARATOR + multipartFile.getName());
                    postId = postService.createPost(postDto);
                    return "redirect:/post_view/" + postId;
                }
                return "redirect:/post_view/";
            } catch (Exception e) {
                log.error(ERROR + NOT_PUBLISH_POST);
                commonsParams.setCommonParams(modelMap);
                return "messages-templates/error";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_POST);
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/error";
        }
    }

    @GetMapping("/post/edit/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String editPost(ModelMap modelMap, @PathVariable long postId) {
        commonsParams.setCommonParams(modelMap);
        postService.checkAuthority(postId);
        modelMap.put("post", postService.getAsDto(postId));
        return "posts/post-edit";
    }

    @PostMapping("/post/edit")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String editPost(PostDto postDto, ModelMap modelMap) {
        // получаем имя юзера для формирования пути сохранения фото
        postService.checkAuthority(postDto.getPostId());
        postService.update(postDto);
        return "redirect:/post_view/" + postDto.getPostId();
    }

    @GetMapping("/post-view-sub/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPostViewSub(@PathVariable long id, ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("post", postService.findById(id));
        modelMap.put("isClosedProfilebyPostId", postService.isClosedProfilebyPostId(id));
        modelMap.put("comments", commentService.findSortedCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("countLikes", likesService.countLikesByPostId(id));
        modelMap.put("isLike", likesService.isLikePostForCurrentUser(id, username));
        return "posts/post-view-sub";
    }

    @GetMapping("/post_view/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPostView(@PathVariable long id, ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentService.findSortedCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("countLikes", likesService.countLikesByPostId(id));
        modelMap.put("isLike", likesService.isLikePostForCurrentUser(id, username));
        return "posts/post-view";
    }

    @DeleteMapping("/post/delete/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable long id) {
        postService.delete(id);
    }

    @GetMapping("/post/delete_one_post/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteOnePost(@PathVariable long id) {
        postService.delete(id);
        return "redirect:/posts_detail";
    }

    @GetMapping("/posts_my_likes")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findMyLikesPosts(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("posts", postService.findMyLikesPosts(username));
        modelMap.put("countPosts", postService.countMyLikesPosts(username));
        return "posts/posts-my-likes";
    }
}