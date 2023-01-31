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

import java.util.*;

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

    @GetMapping("/handshakes_search")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String handshakes_search(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "handshakes/handshakes-search";
    }

    @PostMapping("/handshakes_results")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String handshakes_results(ModelMap modelMap, @RequestParam(required = false) String wantedUsername) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Long userIdListNew = 0L;
        int countHandshakes = 0;
        List<Long> linkUsers = new LinkedList<>();
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
                boolean isFirstPass = true;
                int countHandshakesResult = searchWantedUser(userIdList, userIdListNew, currentUserId, i, userSubIdList, wantedUserId, countHandshakes, isFirstPass);
                List<Long> linkUsersResult = searchLinksWantedUser(userIdList, userIdListNew, currentUserId, i, userSubIdList, wantedUserId, countHandshakes, isFirstPass, linkUsers);

                // Собираем цепочку связей для визуализации на странице
                Map<String,String> resultLinkUsers = new HashMap<>();
                if(linkUsersResult != null) {
                    resultLinkUsers = findLinkUsers(linkUsersResult);
                }
                else {
                    resultLinkUsers.put("0", "0");
                }

                List<Integer> calcCountHandshakesResult = calcCountHandshakes(resultLinkUsers);

                modelMap.put("countHandshakes", countHandshakesResult);
                modelMap.put("wantedUsername", wantedUsername);
                modelMap.put("LinkUsersTree", resultLinkUsers);
                modelMap.put("calcCountHandshakes", calcCountHandshakesResult);

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

    private Map<String,String> findLinkUsers(List<Long> linkUsersResult) {
        List<String> findUserByIdLink = null;
        Map<String,String> resultLinkUsers = new HashMap<>();
        for(int i = 0; i < linkUsersResult.size(); i++) {
            findUserByIdLink = handshakeService.findUsersById(linkUsersResult.get(i));

            String[] findUserByIdLinkArray = findUserByIdLink.toString().split(",");
            String[] linkUsernameSplit = findUserByIdLinkArray[0].split("\\u005b");
            String linkUsername = linkUsernameSplit[1];
            String[] linkUsernamePhotoSplit = findUserByIdLinkArray[1].split("\\u005d");
            String linkUsernamePhoto = linkUsernamePhotoSplit[0];

            resultLinkUsers.put(linkUsername, linkUsernamePhoto);

        }
        return resultLinkUsers;
    }

    List<Integer> calcCountHandshakes(Map<String,String> resultLinkUsers) {
        List<Integer> calcCountHandshakes = new ArrayList<>();
        for(int a = 1; a <= resultLinkUsers.size() - 1; a++) {
            calcCountHandshakes.add(a);
        }
        return calcCountHandshakes;
    }

    private int searchWantedUser(List<Long> userIdList, Long userIdListNew, Long initialUser, int i, List<Long> userSubIdList, Long wantedUser, int countHandshakes, boolean isFirstPass) {
        mainCycle: for(i = 0; i < userIdList.size(); i++) {
        //if(!isFirstPass && i == userIdList.size()-1) {
        //    countHandshakes = 1;
        //}
        // TODO Теперь необходимо учесть если исходного юзера больше 6, то нужно как-то счетчик countHandshakes сбрасывать или ввести новый (общий и по каждой цепочке)
        // TODO Остальное вроде работает правильно
        if(countHandshakes == 0 && isFirstPass) {
            if (userIdList.get(i) == initialUser && isFirstPass) {
                userIdListNew = userSubIdList.get(i);
                if (userIdListNew == wantedUser) {
                    countHandshakes++;
                    break mainCycle;
                }
            }
            if(i == userIdList.size()-1) {
                countHandshakes++;
                isFirstPass = false;
                i=-1;
                continue mainCycle;
            }
        }
        if(countHandshakes > 0 && countHandshakes < 7 && !isFirstPass) {
            if(userIdList.get(i) == initialUser) {
                userIdListNew = userSubIdList.get(i);
            }
            if(userIdListNew == userIdList.get(i)) {
                userIdListNew = userSubIdList.get(i);
                countHandshakes++;
                if (userIdListNew == wantedUser) {
                    break mainCycle;
                }
                int countHandshakesResult = searchWantedUser(userIdList, userIdListNew, initialUser, i, userSubIdList, wantedUser, countHandshakes, isFirstPass);
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

    private List<Long> searchLinksWantedUser(List<Long> userIdList, Long userIdListNew, Long initialUser, int i, List<Long> userSubIdList, Long wantedUser, int countHandshakes, boolean isFirstPass, List<Long> linkUsers) {
        linkUsers.add(initialUser);
        mainCycle: for(i = 0; i < userIdList.size(); i++) {
            if(countHandshakes == 0 && isFirstPass) {
                if (userIdList.get(i) == initialUser && isFirstPass) {
                    userIdListNew = userSubIdList.get(i);
                    if (userIdListNew == wantedUser) {
                        countHandshakes++;
                        linkUsers.add(userIdListNew);
                        break mainCycle;
                    }
                }
                if(i == userIdList.size()-1) {
                    countHandshakes++;
                    isFirstPass = false;
                    i=-1;
                    continue mainCycle;
                }
            }
            if(countHandshakes > 0 && countHandshakes < 7 && !isFirstPass) {
                if(userIdList.get(i) == initialUser) {
                    userIdListNew = userSubIdList.get(i);
                }
                if(userIdListNew == userIdList.get(i)) {
                    linkUsers.add(userIdListNew);
                    userIdListNew = userSubIdList.get(i);
                    countHandshakes++;
                    linkUsers.add(userIdListNew);
                    if (userIdListNew == wantedUser) {
                        linkUsers.add(userIdListNew);
                        break mainCycle;
                    }
                    linkUsers = searchLinksWantedUser(userIdList, userIdListNew, initialUser, i, userSubIdList, wantedUser, countHandshakes, isFirstPass, linkUsers);
                    return linkUsers;
                }
            }
            if(countHandshakes > 6) {
                countHandshakes = 0;
                return linkUsers = null;
            }
        }
        return linkUsers;
    }

}
