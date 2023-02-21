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
    final short LIMIT_SIZE_HANDSHAKE = 6;

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

    // TODO Прокоментировать код и удалить лишнее, сделать проверки на null, в "modelMap.put("countHandshakes", resultLinkUsers.size())"

    @PostMapping("/handshakes_results")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String handshakes_results(ModelMap modelMap, @RequestParam(required = false) String wantedUsername) {
        setCommonParams(modelMap);

        // Получаем Id текущего пользователя
        Long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        //Long currentUserId = userService.findByUsername(getCurrentUserDetails().getUsername()).getUserId().longValue();
        Long userIdNextLink = 0L;

        if (wantedUsername == null || wantedUsername.equals("")) {
            return "handshakes/handshakes-search";
        } else {
            try {
                // Получаем Id искомого пользователя по имени пользователя
                Long wantedUserId = userService.findByUsername(wantedUsername).getUserId().longValue();

                // Делаем выборку левой колонки таблицы
                List<Long> userIdLeftColumnList = new ArrayList<>();
                userIdLeftColumnList = handshakeService.selectUsersId();

                // Делаем выборку правой колонки таблицы
                List<Long> userIdRightColumnList = new ArrayList<>();
                userIdRightColumnList = handshakeService.selectUsersSubId();

                // Осуществляем рекурсивный поиск по колонкам и помещаем все варианты в мапу
                boolean isFirstPass = true;
                List<Long> value = new ArrayList<>(); // Храним отдельно каждую цепочку и потом сохраняем в mapUsers
                Map<Long, List<Long>> mapUsers = new HashMap<>(); // Собираем все совпадения
                int stopCycle = 0; // Нужна, если нет ни одного совпадения - выходим из рекурсии
                Map<Long, List<Long>> countHandshakesResult = searchWantedUser(currentUserId, wantedUserId, userIdNextLink, userIdLeftColumnList, userIdRightColumnList, isFirstPass, value, mapUsers, stopCycle);

                // В полученной мапе ищем кратчайшую связь и записываем в minEntry
                Map.Entry<Long, List<Long>> minEntry = null;
                minEntry = searchMinRelation(countHandshakesResult);

                // Собираем цепочку связей для визуализации на странице в виде иконок
                Map<String, String> resultLinkUsers = new HashMap<>();
                List<Long> usersIds = new ArrayList<>();
                resultLinkUsers = createLinksVisual(usersIds, currentUserId, minEntry);

                // Выводим число рукопожатий - преобразуем в Long. -1 - минусуем текущего пользователя
                Long resultLinkUsersLong = Long.valueOf(resultLinkUsers.size() - 1);

                // Выводим номер рукопожатия для корректной работы условий на странице с результатами
                List<Integer> viewNumberOfHandshake = viewNumberOfHandshakes(resultLinkUsers);

                // Выводим число рукопожатий
                modelMap.put("countHandshakes", resultLinkUsersLong);

                // Выводим имя искомого пользователя
                modelMap.put("wantedUsername", wantedUsername);

                // Собираем цепочку связей для визуализации на странице в виде иконок
                modelMap.put("LinkUsersTree", resultLinkUsers);

                // Выводим номер рукопожатия
                modelMap.put("viewNumberOfHandshake", viewNumberOfHandshake);

                return "handshakes/handshakes-results";
            } catch (Exception e) {
                return "messages-templates/usernotfound";
            }
        }
    }

    private Map<Long, List<Long>> searchWantedUser(Long initialUser, Long wantedUser, Long userIdNextLink, List<Long> userIdLeftColumnList, List<Long> userIdRightColumnList, boolean isFirstPass, List<Long> value, Map<Long, List<Long>> mapUsers, int stopCycle) {

        Long key = 0L;
        boolean countMatched = false;

        firstCycle:
        for (int i = 0; i < userIdLeftColumnList.size(); i++) {
            if (isFirstPass) {
                if (initialUser == userIdLeftColumnList.get(i)) {
                    userIdNextLink = userIdRightColumnList.get(i);
                    value = new ArrayList<>();
                    key++;
                    value.add(userIdNextLink);
                    if (wantedUser == userIdNextLink) {
                        mapUsers.put(key, value);
                        value = new ArrayList<>();
                        break firstCycle;
                    }
                    isFirstPass = false;
                }
            }

            if (!isFirstPass) {
                countMatched = false;
                for (int j = 0; j < userIdLeftColumnList.size(); j++) {
                    if (value.size() > LIMIT_SIZE_HANDSHAKE) {
                        value = new ArrayList<>();
                    }
                    if (userIdNextLink == userIdLeftColumnList.get(j)) {
                        countMatched = true;
                        userIdNextLink = userIdRightColumnList.get(j);
                        key++;
                        value.add(userIdNextLink);
                        if (value.size() > LIMIT_SIZE_HANDSHAKE) {
                            value = new ArrayList<>();
                            isFirstPass = true;
                            continue firstCycle;
                        }
                        if (wantedUser == userIdNextLink) {
                            mapUsers.put(key, value);
                            isFirstPass = true;
                            break firstCycle;
                        }
                    }
                    if (countMatched) {
                        stopCycle = stopCycle + 1;
                        if (stopCycle > LIMIT_SIZE_HANDSHAKE) {
                            return mapUsers;
                        }
                        searchWantedUser(initialUser, wantedUser, userIdNextLink, userIdLeftColumnList, userIdRightColumnList, isFirstPass, value, mapUsers, stopCycle);
                        return mapUsers;
                    }
                    if ((j == userIdLeftColumnList.size() - 1) && !countMatched) {
                        isFirstPass = true;
                        countMatched = false;
                        i++;
                        break;
                    }
                }
            }
        }
        return mapUsers;
    }

    // Метод, собирающий цепочку связей для визуализации на странице в виде иконок
    private Map<String, String> findLinkUsers(List<Long> linkUsersResult) {
        List<String> findUserByIdLink = null;
        Map<String, String> resultLinkUsers = new HashMap<>();
        for (int i = 0; i < linkUsersResult.size(); i++) {
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

    // Метод, который выводит номер связи
    List<Integer> viewNumberOfHandshakes(Map<String, String> resultLinkUsers) {
        List<Integer> calcCountHandshakes = new ArrayList<>();
        for (int a = 1; a <= resultLinkUsers.size() - 1; a++) {
            calcCountHandshakes.add(a);
        }
        return calcCountHandshakes;
    }

    // Метод, в котором ищем кратчайшую связь и записываем в minEntry
    private Map.Entry<Long, List<Long>> searchMinRelation(Map<Long, List<Long>> countHandshakesResult) {
        Map.Entry<Long, List<Long>> minEntry = null;
        for (Map.Entry<Long, List<Long>> entry : countHandshakesResult.entrySet()) {
            if (minEntry == null || entry.getValue().size() <= minEntry.getValue().size()) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    // Метод, собирающий цепочку связей для визуализации на странице в виде иконок
    Map<String, String> createLinksVisual(List<Long> usersIds, Long currentUserId, Map.Entry<Long, List<Long>> minEntry) {
        Map<String, String> resultLinkUsers = new HashMap<>();
        if (minEntry != null) {
            usersIds.add(currentUserId);
            usersIds.addAll(minEntry.getValue());
            resultLinkUsers = findLinkUsers(usersIds);

        } else {
            resultLinkUsers.clear();
        }
        return resultLinkUsers;
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

}
