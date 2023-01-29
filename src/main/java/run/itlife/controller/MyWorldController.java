package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.service.PostService;
import run.itlife.service.UserService;

@Controller
public class MyWorldController {

    private final UserService userService;
    private final PostService postService;

    @Autowired
    public MyWorldController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/my_world")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String index(ModelMap modelMap) {
        setCommonParams(modelMap);
        modelMap.put("findTags", postService.searchTags(""));

        return "myworld/my-world";
    }

    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
    }
}
