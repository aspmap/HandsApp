package run.itlife;

import org.junit.Test;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import run.itlife.controller.PostController;
import run.itlife.repository.UserRepository;
import run.itlife.service.*;

import javax.servlet.ServletContext;

import static org.junit.Assert.assertEquals;

public class PostControllerTest {

 /*   private final PostService postService;
    private final LikesService likesService;
    private final UserService userService;
    private final CommentService commentService;
    private final SubscriptionsService subscriptionsService;
    private final UserRepository userRepository;
    private final ServletContext context;

    public PostControllerTest(PostService postService, LikesService likesService, UserService userService, CommentService commentService, SubscriptionsService subscriptionsService, UserRepository userRepository, ServletContext context) {
        this.postService = postService;
        this.likesService = likesService;
        this.userService = userService;
        this.commentService = commentService;
        this.subscriptionsService = subscriptionsService;
        this.userRepository = userRepository;
        this.context = context;
    }

    @Test
    public void testpost() {
        PostController pc = new PostController(postService, likesService, userService, commentService, context, subscriptionsService, userRepository);
        String result = pc.post(@PathVariable long id, ModelMap modelMap);
        assertEquals("posts/post-view", PostController.);
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
    }*/
}
