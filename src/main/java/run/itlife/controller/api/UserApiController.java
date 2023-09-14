package run.itlife.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.itlife.service.SubscriptionsService;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    private final SubscriptionsService subscriptionsService;

    @Autowired
    public UserApiController(SubscriptionsService subscriptionsService) {
        this.subscriptionsService = subscriptionsService;
    }

    @GetMapping("/subscribers/{username}")
    public int findSubscribers(@PathVariable String username) {
        return subscriptionsService.countSubscribe(username);
    }

}
