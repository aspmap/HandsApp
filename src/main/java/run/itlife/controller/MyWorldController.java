package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.service.PostService;
import run.itlife.utils.CommonsParams;

@Controller
public class MyWorldController {
    private final PostService postService;
    @Autowired
    CommonsParams commonsParams;

    @Autowired
    public MyWorldController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/my_world")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String index(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("findTags", postService.searchTags(""));

        return "myworld/my-world";
    }
}
