package run.itlife.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {

    @GetMapping("/api")
    public String index(ModelMap modelMap) {
        return "api/api";
    }
}
