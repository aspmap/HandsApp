package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.utils.CommonsParams;

@Controller
public class ApiController {
    @Autowired
    CommonsParams commonsParams;

    @GetMapping("/api")
    public String index(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "api/api";
    }
}
