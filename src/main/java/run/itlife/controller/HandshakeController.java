package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.entity.Role;
import run.itlife.entity.User;
import run.itlife.service.HandshakeService;
import run.itlife.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static run.itlife.utils.SecurityUtils.getCurrentUserDetails;

@Controller
public class HandshakeController {

    private final HandshakeService handshakeService;
    private final UserService userService;

    @Autowired
    public HandshakeController(HandshakeService handshakeService, UserService userService) {
        this.handshakeService = handshakeService;
        this.userService = userService;
    }

    @GetMapping("/handshakes-search")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String handshakes_search(ModelMap modelMap) {
        setCommonParams(modelMap);

        // блокируем страницу для пользователей при обращении напрямую
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userCheckRole = userService.findByUsername(username);
        if(!userCheckRole.getUsername().isEmpty()) {
            for (Role u: userCheckRole.getRoles()) {
                if(u.getName().equals("USER")) {
                    return "messages-templates/404";
                }
            }
        }
        return "handshakes/handshakes-search";
    }

    @PostMapping("/handshakes-results")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String handshakes_results(ModelMap modelMap, @RequestParam(required = false) String wantedUsername) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // блокируем страницу для пользователей при обращении напрямую
        User userCheckRole = userService.findByUsername(username);
        if(!userCheckRole.getUsername().isEmpty()) {
            for (Role u: userCheckRole.getRoles()) {
                if(u.getName().equals("USER")) {
                    return "messages-templates/404";
                }
            }
        }

        Long userIdListNew = 0L;
        int countHandshakes = 0;
        int i = 0;

        Long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        if(wantedUsername == null || wantedUsername.equals("")) {
            return "handshakes/handshakes-search";
        }
        else{
            try {
                Long wantedUserId = userService.findByUsername(wantedUsername).getUserId().longValue();

                // Делаем выборку левой колонки таблицы
                List<Long> userIdList = new ArrayList<>();
                userIdList = handshakeService.selectUsersId();

                // Делаем выборку правой колонки таблицы
                List<Long> userSubIdList = new ArrayList<>();
                userSubIdList = handshakeService.selectUsersSubId();

                // Осуществляем рекурсивный поиск по колонкам
                int countHandshakesResult = searchWantedUser(userIdList, userIdListNew, currentUserId, i, userSubIdList, wantedUserId, countHandshakes);

                modelMap.put("countHandshakes", countHandshakesResult);
                modelMap.put("wantedUsername", wantedUsername);

                return "handshakes/handshakes-results";
            }
            catch (Exception e) {
                return "messages-templates/usernotfound";
            }
        }
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

    private int searchWantedUser(List<Long> userIdList, Long userIdListNew, Long initialUser, int i, List<Long> userSubIdList, Long wantedUser, int countHandshakes) {
        exitCycle: for(i = 0; i < userIdList.size(); i++) {
            if(countHandshakes == 0) {
                if(userIdList.get(i) == initialUser) {
                    userIdListNew = userSubIdList.get(i);
                    countHandshakes++;
                    if(userIdListNew == wantedUser) {
                        break exitCycle;
                    }
                    int countHandshakesResult = searchWantedUser(userIdList, userIdListNew, initialUser, i, userSubIdList, wantedUser, countHandshakes);
                    return countHandshakesResult;
                }
            }
            if(countHandshakes > 0 || countHandshakes < 7) {
                if(userIdListNew == userIdList.get(i)) {
                    userIdListNew = userSubIdList.get(i);
                    countHandshakes++;
                    if (userIdListNew == wantedUser) {
                        break exitCycle;
                    }
                    int countHandshakesResult = searchWantedUser(userIdList, userIdListNew, initialUser, i, userSubIdList, wantedUser, countHandshakes);
                    return countHandshakesResult;
                }
            }
            if(countHandshakes > 6) {
                countHandshakes = 0;
                return countHandshakes;
            }
        }
        return countHandshakes;
    }

}
