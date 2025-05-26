package run.itlife.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.entity.Post;
import run.itlife.service.PostPaginationService;
import run.itlife.service.PostService;
import run.itlife.service.UserService;

import javax.servlet.ServletContext;
import java.util.ArrayList;

@Controller
public class PostPaginationController {
    private final UserService userService;
    private final PostService postService;
    private final PostPaginationService postPaginationService;
    private final ServletContext context;
    @Autowired
    ServletContext servletContext;
    private static final Logger log = LoggerFactory.getLogger(PostPaginationController.class);

    @Autowired
    public PostPaginationController(UserService userService, PostService postService, PostPaginationService postPaginationService, ServletContext context) {
        this.userService = userService;
        this.postService = postService;
        this.postPaginationService = postPaginationService;
        this.context = context;
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String getSubscribesPosts(ModelMap modelMap, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String sortBy) {
        ArrayList<Integer> pages = new ArrayList<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> myDataPage = postPaginationService.findSubscribesPosts(username, pageable);
        Integer totalPages = myDataPage.getTotalPages();
        for (int i = 0; i < totalPages; i++) {
            pages.add(i);
        }
        modelMap.put("currentPage", page);
        modelMap.put("pages", pages);
        modelMap.put("countPosts", postService.countSubscribesPosts(username));
        modelMap.put("isYourLike", postService.isLikePost(username)); // TODO как выдернуть id поста??
        modelMap.put("posts_sub", myDataPage);
        setCommonParams(modelMap);
        return "posts/pagination_posts";
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String getSubscribesPostsScroll(ModelMap modelMap, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String sortBy) throws JsonProcessingException {
        ArrayList<Integer> pages = new ArrayList<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> myDataPage = postPaginationService.findSubscribesPosts(username, pageable);
        Integer totalPages = myDataPage.getTotalPages();
        for (int i = 0; i < totalPages; i++) {
            pages.add(i);
        }
        modelMap.put("size", size);
        modelMap.put("totalPages", myDataPage.getTotalPages());
        modelMap.put("isYourLike", postService.isLikePost(username)); // TODO как выдернуть id поста??
        modelMap.put("userPhotoCurrent", userService.findByUsername(username).getPhoto());
        setCommonParams(modelMap);
        return "posts/infinite_scroll_posts";
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
}