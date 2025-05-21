package run.itlife.controller.microservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.service.UserService;
import run.itlife.utils.JWTtoken;

import javax.servlet.ServletContext;

@Controller
public class PostControllerMicroService {
    private final UserService userService;
    private final String msServiceUrl;
    private final ServletContext context;
    @Autowired
    JWTtoken jwTtoken;
    private static final Logger log = LoggerFactory.getLogger(PostControllerMicroService.class);

    @Autowired
    public PostControllerMicroService(UserService userService, @Value("${ms.service.url}") String msServiceUrl, ServletContext context) {
        this.userService = userService;
        this.msServiceUrl = msServiceUrl;
        this.context = context;
    }

    @GetMapping("posts_ms/")
    @PreAuthorize("hasRole('USER')")
    public String getPosts(ModelMap modelMap, Authentication authentication) {
        String token = jwTtoken.generateToken(authentication);
        modelMap.put("bToken", token);
        setCommonParams(modelMap);
        return "microservice/posts";
    }

    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userPhoto", userService.findByUsername(username).getPhoto());
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
        modelMap.put("contextPath", context.getContextPath());
        modelMap.put("msServiceUrl", msServiceUrl);
    }
}
