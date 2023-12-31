package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.dto.CommentDto;
import run.itlife.service.CommentService;
import run.itlife.service.PostService;
import run.itlife.service.UserService;

//Контроллер для комментариев (создание)
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    //Принимает он CommentDto - берёт данные с формы post.html, видит, что там есть name=”postId”, есть name="content”
    //и т.д. и он маппит по этим именам на объект CommentDto
    public String create(CommentDto comment){
        commentService.create(comment);
        return "redirect:/post/" + comment.getPostId();
    }

    @GetMapping("/delete/{postId}/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public String delete(@PathVariable long commentId, @PathVariable long postId){
        commentService.delete(commentId);
        return "redirect:/comment/" + postId;
    }

    @GetMapping("/delete/sub/{postId}/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_sub(@PathVariable long commentId, @PathVariable long postId){
        commentService.delete(commentId);
        return "redirect:/comment/sub/" + postId;
    }

    @PostMapping("/create_subuser_comment")
    @PreAuthorize("hasRole('USER')")
    public String create_subuser_comment(CommentDto comment){
        commentService.create(comment);
        return "redirect:/post-view-sub/" + comment.getPostId();
    }

    @PostMapping("/create_comment_detail")
    @PreAuthorize("hasRole('USER')")
    public String create_comment_detail(CommentDto comment){
        commentService.create(comment);
        return "redirect:/posts_detail/";
    }

    @GetMapping("/create_comment_detail_subuser/{user}")
    @PreAuthorize("hasRole('USER')")
    public String create_comment_detail_subuser(CommentDto comment, @PathVariable String user){
        commentService.create(comment);
        return "redirect:/posts_detail_subuser/" + user;
    }

    @PostMapping("/create_comment_detail_sub")
    @PreAuthorize("hasRole('USER')")
    public String create_comment_detail_sub(CommentDto comment){
        commentService.create(comment);
        return "redirect:/";
    }

    @PostMapping("/create_comment")
    @PreAuthorize("hasRole('USER')")
    public String create_comment(CommentDto comment){
        commentService.create(comment);
        return "redirect:/comment/" + comment.getPostId();
    }

    @PostMapping("/create_comment_subuser")
    @PreAuthorize("hasRole('USER')")
    public String create_comment_subuser(CommentDto comment){
        commentService.create(comment);
        return "redirect:/comment/sub/" + comment.getPostId();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public String comments(@PathVariable long id, ModelMap modelMap){
        setCommonParams(id, modelMap);
        return "comments/comments";
    }

    @GetMapping("/sub/{id}")
    @PreAuthorize("hasRole('USER')")
    public String comments_sub(@PathVariable long id, ModelMap modelMap){
        setCommonParams(id, modelMap);
        return "comments/comments-sub";
    }

    private void setCommonParams(long id, ModelMap modelMap) {
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentService.sortCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userslist", userService.findAll());
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
    }

}