package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.service.SubscriptionsService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;

@Controller
@RequestMapping("/page")
public class SubscriptionsController {
    private final UserService userService;
    private final SubscriptionsService subscriptionsService;
    @Autowired
    CommonsParams commonsParams;

    @Autowired
    public SubscriptionsController(UserService userService, SubscriptionsService subscriptionsService) {
        this.userService = userService;
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPostsSub(ModelMap modelMap, @PathVariable String user) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commonsParams.setCommonSubParams(modelMap, user);
        commonsParams.setCommonParams(modelMap);
        modelMap.put("isSub", subscriptionsService.isSubscribe(username, user));
        return "posts/view/subscriber-page";
    }

    /**
     * Подписка в рекомендациях
     */
    @GetMapping("/subscription_from_recommendations/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createSubscribeFromRecommendations(ModelMap modelMap, @PathVariable String user){
        subscriptionsService.createSub(user);
        return "redirect:/";
    }

    @GetMapping("/subscription/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createSubscribe(@PathVariable String user){
        subscriptionsService.createSub(user);
        return "redirect:/page/{user}";
    }

    @GetMapping("/unsubscription/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String unsubscribe(@PathVariable String user){
        long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        long subUserId = userService.findByUsername(user).getUserId().longValue();
        subscriptionsService.deleteSubscribeLong(currentUserId, subUserId);
        return "redirect:/page/{user}";
    }
}
