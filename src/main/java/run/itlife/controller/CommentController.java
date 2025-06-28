package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.dto.CommentDto;
import run.itlife.service.CommentService;
import run.itlife.utils.CommonsParams;

//Контроллер для комментариев (создание)
@Controller
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    CommonsParams commonsParams;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    //Принимает он CommentDto - берёт данные с формы post.html, видит, что там есть name="postId", есть name="content"
    //и т.д. и он маппит по этим именам на объект CommentDto
    public String createCommentInPost(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/post_view/" + comment.getPostId();
    }

    @PostMapping("/create_in_comments")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentInComments(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/comments/" + comment.getPostId();
    }

    @PostMapping("/create_comment_in_subscriber_post")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentInSubscriberPost(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/post_view_of_subscriber/" + comment.getPostId();
    }

    @PostMapping("/create_comment_in_posts_detail")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentInPostsDetail(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/posts_detail/";
    }

    @GetMapping("/create_comment_in_posts_detail_of_subscriber/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentInPostsDetailOfSubscriber(CommentDto comment, @PathVariable String user){
        commentService.createComment(comment);
        return "redirect:/create_comment_in_posts_detail_of_subscriber/" + user;
    }

    @PostMapping("/create_comment_in_posts_detail_of_subscriber")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentDetailSub(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/";
    }

    @PostMapping("/create_comment_in_comments_of_subscriber")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentInCommentsOfSubscriber(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/comments/comments_subscriber/" + comment.getPostId();
    }

    @GetMapping("/delete/{postId}/{commentId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteComment(@PathVariable long commentId, @PathVariable long postId){
        commentService.deleteComment(commentId);
        return "redirect:/comments/" + postId;
    }

    @GetMapping("/delete_in_comments/{postId}/{commentId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteCommentInComments(@PathVariable long commentId, @PathVariable long postId){
        commentService.deleteComment(commentId);
        return "redirect:/comments/comments_subscriber/" + postId;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findCommentById(@PathVariable long id, ModelMap modelMap){
        commonsParams.setCommonParams(id, modelMap);
        return "comments/comments";
    }

    @GetMapping("/comments_subscriber/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findCommentByIdInSubscriber(@PathVariable long id, ModelMap modelMap){
        commonsParams.setCommonParams(id, modelMap);
        return "comments/comments-subscriber";
    }
}