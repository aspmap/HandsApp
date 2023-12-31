package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import run.itlife.service.LikesService;
import run.itlife.service.UserService;

import static run.itlife.utils.SecurityUtils.getCurrentUserDetails;

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
    public String create_like(@PathVariable long postId){
        likesService.create_like(postId);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/unlike/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        //long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/post/{postId}";
    }

    @GetMapping("/like_view_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like_sub(@PathVariable long postId){
        likesService.create_like(postId);
        return "redirect:/post-view-sub/{postId}";
    }

    @GetMapping("/unlike_view_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like_sub(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        //long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/post-view-sub/{postId}";
    }

    @GetMapping("/like_detail/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like_detail(@PathVariable long postId){
        likesService.create_like(postId);
        return "redirect:/posts_detail";
    }

    @GetMapping("/unlike_detail/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like_detail(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        //long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/posts_detail";
    }

    @GetMapping("/like_detail_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like_detail_sub(@PathVariable long postId){
        likesService.create_like(postId);
        return "redirect:/";
    }

    @GetMapping("/unlike_detail_sub/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like_detail_sub(@PathVariable long postId){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        //long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/";
    }

    @GetMapping("/like_detail_subuser/{user}/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String create_like_detail_subuser(@PathVariable long postId, @PathVariable String user){
        likesService.create_like(postId);
        return "redirect:/posts_detail_subuser/{user}";
    }

    @GetMapping("/unlike_detail_subuser/{user}/{postId}")
    @PreAuthorize("hasRole('USER')")
    public String delete_like_detail_subuser(@PathVariable long postId, @PathVariable String user){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        // long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        likesService.delete_like(currentUserId, postId);
        return "redirect:/posts_detail_subuser/{user}";
    }

}
