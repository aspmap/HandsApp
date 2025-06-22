package run.itlife.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import run.itlife.service.*;

import javax.servlet.ServletContext;
import java.time.LocalDateTime;

@Component
public class CommonsParams {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final SubscriptionsService subscriptionsService;
    private final DialogsService dialogsService;
    private final ServletContext context;
    private final VersionProject versionProject;

    @Autowired
    public CommonsParams(UserService userService, PostService postService, CommentService commentService, SubscriptionsService subscriptionsService, DialogsService dialogsService, ServletContext context, VersionProject versionProject) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.subscriptionsService = subscriptionsService;
        this.dialogsService = dialogsService;
        this.context = context;
        this.versionProject = versionProject;
    }

    public void setCommonParams(long id, ModelMap modelMap) {
        modelMap.put("post", postService.findById(id));
        modelMap.put("comments", commentService.findSortedCommentsByDate(id));
        modelMap.put("countComments", postService.countComments(id));
        this.setCommonParams(modelMap);
    }

    public void setCommonParams(ModelMap modelMap, String username) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.findUsersOnly());
        modelMap.put("usersOnlyKey", userService.findUsersOnlyKey(username));
        modelMap.put("contextPath", context.getContextPath());
        this.setCommonConstParams(modelMap);
    }

    public void setCommonSubParams(ModelMap modelMap, String username) {
        modelMap.put("userinfo_sub", userService.findByUsername(username));
        modelMap.put("user_sub", username);
        modelMap.put("posts", postService.findSortedPostsByDate(username));
        modelMap.put("countPosts", postService.countPosts(username));
        modelMap.put("isClosedProfile", postService.isClosedProfile(username));
        modelMap.put("countSubscribe", subscriptionsService.countSubscribe(username));
        modelMap.put("countSubscribers", subscriptionsService.countSubscribers(username));
        this.setCommonConstParams(modelMap);
    }

    public void setCommonParams(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.findUsersOnly());
        modelMap.put("usersOnlyKey", userService.findUsersOnlyKey(username));
        modelMap.put("userPhoto", userService.findByUsername(username).getPhoto());
        modelMap.put("unreadMessagesTotal", dialogsService.findUnreadDialogs(username).size());
        modelMap.put("contextPath", context.getContextPath());
        this.setCommonConstParams(modelMap);
    }

    public void setCommonConstParams(ModelMap modelMap) {
        modelMap.put("majorVersion", versionProject.getMajorVersion());
        modelMap.put("minorVersion", versionProject.getMinorVersion());
        modelMap.put("microVersion", versionProject.getMicroVersion());
        modelMap.put("stageVersion", versionProject.getStageVersion());
        modelMap.put("currentYear", LocalDateTime.now().getYear());
    }
}
