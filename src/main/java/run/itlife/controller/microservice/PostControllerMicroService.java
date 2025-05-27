package run.itlife.controller.microservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.JWTtoken;

@Controller
public class PostControllerMicroService {
    private final String msServiceUrl;
    @Autowired
    CommonsParams commonsParams;
    @Autowired
    JWTtoken jwTtoken;
    private static final Logger log = LoggerFactory.getLogger(PostControllerMicroService.class);

    @Autowired
    public PostControllerMicroService(@Value("${ms.service.url}") String msServiceUrl) {
        this.msServiceUrl = msServiceUrl;
    }

    @GetMapping("posts_ms/")
    @PreAuthorize("hasRole('USER')")
    public String getPosts(ModelMap modelMap, Authentication authentication) {
        String token = jwTtoken.generateToken(authentication);
        modelMap.put("bToken", token);
        modelMap.put("msServiceUrl", msServiceUrl);
        commonsParams.setCommonParams(modelMap);
        return "microservice/posts";
    }
}
