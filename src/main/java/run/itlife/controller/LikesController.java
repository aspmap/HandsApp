package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.service.LikesService;
import run.itlife.service.UserService;

@Controller
@RequestMapping("/likes")
public class LikesController {
    private final LikesService likesService;
    private final UserService userService;

    @Autowired
    public LikesController(LikesService likesService, UserService userService) {
        this.likesService = likesService;
        this.userService = userService;
    }

    @GetMapping("/create/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createLike(@PathVariable long postId){
        likesService.createLike(postId);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/delete/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteLike(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/like_view_of_subscriber/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createLikeOfSubscriber(@PathVariable long postId){
        likesService.createLike(postId);
        return "redirect:/post_subscriber/{postId}";
    }

    @GetMapping("/unlike_view_of_subscriber/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteLikeOfSubscriber(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/post_subscriber/{postId}";
    }

    @GetMapping("/like_in_posts_detail/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createLikeInPostsDetail(@PathVariable long postId){
        likesService.createLike(postId);
        return "redirect:/posts";
    }

    @GetMapping("/unlike_in_posts_detail/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteLikeInPostsDetail(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/posts";
    }

    @GetMapping("/like_in_subscribers/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createLikeInSubscribers(@PathVariable long postId){
        likesService.createLike(postId);
        return "redirect:/";
    }

    @GetMapping("/unlike_in_subscribers/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteLikeInSubscribers(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/";
    }

    @GetMapping("/like_detail_in_subscriber/{user}/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createLikeDetailInSubscriber(@PathVariable long postId, @PathVariable String user){
        likesService.createLike(postId);
        return "redirect:/posts_subscriber/{user}";
    }

    @GetMapping("/unlike_detail_in_subscriber/{user}/{postId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String deleteLikeDetailInSubscriber(@PathVariable long postId, @PathVariable String user){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/posts_subscriber/{user}";
    }

}
