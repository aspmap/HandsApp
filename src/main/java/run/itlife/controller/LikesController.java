package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import run.itlife.service.LikesService;
import run.itlife.service.UserService;

@Controller
public class LikesController {
    private final LikesService likesService;
    private final UserService userService;

    @Autowired
    public LikesController(LikesService likesService, UserService userService) {
        this.likesService = likesService;
        this.userService = userService;
    }

    @GetMapping("/like/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String createLike(@PathVariable long postId){
        likesService.createLike(postId);
        return "redirect:/post_view/{postId}";
    }

    @GetMapping("/unlike/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String deleteLike(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/post_view/{postId}";
    }

    @GetMapping("/like_view_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String createLikeSub(@PathVariable long postId){
        likesService.createLike(postId);
        return "redirect:/post-view-sub/{postId}";
    }

    @GetMapping("/unlike_view_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String deleteLikeSub(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/post-view-sub/{postId}";
    }

    @GetMapping("/like_detail/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String createLikeDetail(@PathVariable long postId){
        likesService.createLike(postId);
        return "redirect:/posts_detail";
    }

    @GetMapping("/unlike_detail/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String deleteLikeDetail(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/posts_detail";
    }

    @GetMapping("/like_detail_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String createLikeDetailSub(@PathVariable long postId){
        likesService.createLike(postId);
        return "redirect:/";
    }

    @GetMapping("/unlike_detail_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String deleteLikeDetailSub(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/";
    }

    @GetMapping("/like_detail_subuser/{user}/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String createLikeDetailSubuser(@PathVariable long postId, @PathVariable String user){
        likesService.createLike(postId);
        return "redirect:/posts_detail_subuser/{user}";
    }

    @GetMapping("/unlike_detail_subuser/{user}/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String deleteLikeDetailSubuser(@PathVariable long postId, @PathVariable String user){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        likesService.deleteLike(currentUserId, postId);
        return "redirect:/posts_detail_subuser/{user}";
    }

}
