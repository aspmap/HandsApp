package run.itlife.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.Profile;

import javax.servlet.ServletContext;
import java.io.File;

import static run.itlife.utils.Properties.Paths.PATH_IMAGE_USERS;
import static run.itlife.utils.Properties.Paths.PATH_VIDEO_USERS;

@Controller
@RequestMapping("/admin")
public class UserAdminController {
    @Autowired
    CommonsParams commonsParams;
    private final UserService userService;
    private final ServletContext context;
    private Logger log = LoggerFactory.getLogger(UserAdminController.class);

    public UserAdminController(UserService userService, ServletContext context) {
        this.userService = userService;
        this.context = context;
    }

    @GetMapping("/all_users")
    @PreAuthorize("hasRole('ADMIN')")
    public String findAllUsers(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("countSearchUsers", userService.findAllUsers().size());
        modelMap.put("findUsers", userService.findAllUsers());
        return "admin/all-users";
    }

    @GetMapping("/user_profile_delete/{user}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUserProfileGet(ModelMap modelMap, @PathVariable String user) {
        commonsParams.setCommonParams(modelMap, user);
        return "profile/profile-delete";
    }

    @PostMapping("/user_profile_delete/{user}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUserProfilePost(ModelMap modelMap, @PathVariable String user) {
        userService.deleteProfile(user);
        //удаляем папки и файлы пользователя
        File dir_img = new File(context.getRealPath(PATH_IMAGE_USERS + user));
        File dir_video = new File(context.getRealPath(PATH_VIDEO_USERS + user));
        Profile.recursiveFilesDelete(dir_img);
        Profile.recursiveFilesDelete(dir_video);
        return "redirect:/admin/all_users";
    }
}
