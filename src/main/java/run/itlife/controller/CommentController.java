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
@RequestMapping("/comment")
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

    @PostMapping("/create_comment")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentInComments(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/comment/" + comment.getPostId();
    }

    @GetMapping("/delete/{postId}/{commentId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteComment(@PathVariable long commentId, @PathVariable long postId){
        commentService.deleteComment(commentId);
        return "redirect:/comment/" + postId;
    }

    @GetMapping("/delete/sub/{postId}/{commentId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteSubComment(@PathVariable long commentId, @PathVariable long postId){
        commentService.deleteComment(commentId);
        return "redirect:/comment/comments_sub/" + postId;
    }

    @PostMapping("/create_subuser_comment")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createSubuserComment(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/post-view-sub/" + comment.getPostId();
    }

    @PostMapping("/create_comment_detail")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentDetail(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/posts_detail/";
    }

    @GetMapping("/create_comment_detail_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentDetailSubuser(CommentDto comment, @PathVariable String user){
        commentService.createComment(comment);
        return "redirect:/posts_detail_subuser/" + user;
    }

    @PostMapping("/create_comment_detail_sub")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentDetailSub(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/";
    }

    @PostMapping("/create_comment_subuser")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createCommentSubuser(CommentDto comment){
        commentService.createComment(comment);
        return "redirect:/comment/comments_sub/" + comment.getPostId();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findCommentsById(@PathVariable long id, ModelMap modelMap){
        commonsParams.setCommonParams(id, modelMap);
        return "comments/comments";
    }

    @GetMapping("/comments_sub/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findCommentsSubById(@PathVariable long id, ModelMap modelMap){
        commonsParams.setCommonParams(id, modelMap);
        return "comments/comments-sub";
    }
}