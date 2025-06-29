package run.itlife.controller;

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
import run.itlife.utils.CommonsParams;
import run.itlife.utils.VersionProject;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class PostPaginationController {
    private final UserService userService;
    private final PostService postService;
    private final PostPaginationService postPaginationService;
    @Autowired
    ServletContext servletContext;
    @Autowired
    VersionProject versionProject;
    @Autowired
    CommonsParams commonsParams;
    private static final Logger log = LoggerFactory.getLogger(PostPaginationController.class);

    @Autowired
    public PostPaginationController(UserService userService, PostService postService, PostPaginationService postPaginationService) {
        this.userService = userService;
        this.postService = postService;
        this.postPaginationService = postPaginationService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findSubscribesPosts(ModelMap modelMap, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String sortBy) {
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
        commonsParams.setCommonParams(modelMap);

        return "posts/view/posts-pagination";
    }

    @GetMapping("/posts_infinite_scroll")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findSubscribesPostsScroll(ModelMap modelMap, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String sortBy) throws IOException {
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
        commonsParams.setCommonParams(modelMap);
        return "posts/view/posts-infinite-scroll";
    }
}