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


                Map<Long, List<Long>> countHandshakesResult = searchWantedUser(currentUserId, wantedUserId, userIdLeftColumnList, userIdRightColumnList);
                //Map<Long, List<Long>> countHandshakesResult = searchWantedUser123(value, key, mapUsers, isFirstPass, countInitialUserNext, countInitialUser, userIdNextLink, currentUserId, wantedUserId, userIdLeftColumnList, userIdRightColumnList);
                //Map<Long, List<Long>> countHandshakesResult = searchWantedUser(isEnd, countInitialUser, countInitialUserNext, currentUserId, wantedUserId, userIdNextLink, userIdLeftColumnList, userIdRightColumnList, isFirstPass, value, mapUsers, stopCycle);

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

    /**
     * @param initialUser          Текущий пользователь
     * @param userIdLeftColumnList Список пользователей
     * @return countInitialUser Возвращается максимальное количество возможных связей
     */
    private int countInitialUser(Long initialUser, List<Long> userIdLeftColumnList) {
        int countInitialUser = 0;
        for (int i = 0; i < userIdLeftColumnList.size(); i++) {
            if (userIdLeftColumnList.get(i) == initialUser) {
                countInitialUser++;
            }
        }
        return countInitialUser;
    }

    /**
     * Метод, осуществляющий поиск связей (тестовый вариант)
     *
     * @param initialUser           Текущий пользователь
     * @param wantedUser            Искомый пользователь
     * @param userIdLeftColumnList  Список пользователей
     * @param userIdRightColumnList Список пользователей
     * @return mapUsers - возвращаем все найденные связи
     */
    private Map<Long, List<Long>> searchWantedUser(Long initialUser, Long wantedUser, List<Long> userIdLeftColumnList, List<Long> userIdRightColumnList) {
        // Складываем найденные цепочки
        List<Long> value = new ArrayList<>();
        Long key = 0L;
        Map<Long, List<Long>> mapUsers = new HashMap<>();
        // Считаем максимальное количество возможных связей
        int countInitialUserNext = 0;
        int countInitialUser = countInitialUser(initialUser, userIdLeftColumnList);
        // Текущий пользователь
        Long userIdNextLink = 0L;

        firstCycle:
        for (int i = 0; i < userIdLeftColumnList.size(); i++) {
         //   if (countInitialUserNext < countInitialUser) {
                if (initialUser == userIdLeftColumnList.get(i)) {
                    userIdNextLink = userIdRightColumnList.get(i);
                    countInitialUserNext = countInitialUserNext + 1;
                    value = new ArrayList<>();
                    key++;
                    value.add(userIdNextLink);
                    if (value.size() > 6) {
                        value = new ArrayList<>();
                        continue firstCycle;
                    }
                    if (wantedUser == userIdNextLink) {
                        mapUsers.put(key, value);
                        value = new ArrayList<>();
                        break firstCycle;
                    }
                    secondCycle:
                    for (int j = 0; j < userIdLeftColumnList.size(); j++) {
                        if (userIdNextLink == userIdLeftColumnList.get(j)) {
                            userIdNextLink = userIdRightColumnList.get(j);
                            key++;
                            value.add(userIdNextLink);
                            if (value.size() > 6) {
                                value = new ArrayList<>();
                                continue firstCycle;
                            }
                            if (wantedUser == userIdNextLink) {
                                mapUsers.put(key, value);
                                value = new ArrayList<>();
                                break secondCycle;
                            }
                            thirdCycle:
                            for (int k = 0; k < userIdLeftColumnList.size(); k++) {
                                if (userIdNextLink == userIdLeftColumnList.get(k)) {
                                    userIdNextLink = userIdRightColumnList.get(k);
                                    key++;
                                    value.add(userIdNextLink);
                                    if (value.size() > 6) {
                                        value = new ArrayList<>();
                                        continue firstCycle;
                                    }
                                    if (wantedUser == userIdNextLink) {
                                        mapUsers.put(key, value);
                                        value = new ArrayList<>();
                                        break thirdCycle;
                                    }
                                    thirthCycle:
                                    for (int l = 0; l < userIdLeftColumnList.size(); l++) {
                                        if (userIdNextLink == userIdLeftColumnList.get(l)) {
                                            userIdNextLink = userIdRightColumnList.get(l);
                                            key++;
                                            value.add(userIdNextLink);
                                            if (value.size() > 6) {
                                                value = new ArrayList<>();
                                                continue firstCycle;
                                            }
                                            if (wantedUser == userIdNextLink) {
                                                mapUsers.put(key, value);
                                                value = new ArrayList<>();
                                                break thirthCycle;
                                            }
                                            firthCycle:
                                            for (int m = 0; m < userIdLeftColumnList.size(); m++) {
                                                if (userIdNextLink == userIdLeftColumnList.get(m)) {
                                                    userIdNextLink = userIdRightColumnList.get(m);
                                                    key++;
                                                    value.add(userIdNextLink);
                                                    if (value.size() > 6) {
                                                        value = new ArrayList<>();
                                                        continue firstCycle;
                                                    }
                                                    if (wantedUser == userIdNextLink) {
                                                        mapUsers.put(key, value);
                                                        value = new ArrayList<>();
                                                        break firthCycle;
                                                    }
                                                    sixthCycle:
                                                    for (int n = 0; n < userIdLeftColumnList.size(); n++) {
                                                        if (userIdNextLink == userIdLeftColumnList.get(n)) {
                                                            userIdNextLink = userIdRightColumnList.get(n);
                                                            key++;
                                                            value.add(userIdNextLink);
                                                            if (value.size() > 6) {
                                                                value = new ArrayList<>();
                                                                continue firstCycle;
                                                            }
                                                            if (wantedUser == userIdNextLink) {
                                                                mapUsers.put(key, value);
                                                                value = new ArrayList<>();
                                                                break sixthCycle;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
           // }
        }
        return mapUsers;
    }

    /**
     * Метод, собирающий цепочку связей для визуализации на странице в виде иконок
     *
     * @param linkUsersResult
     * @return
     */
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

    /**
     * Метод, который выводит номер связи
     *
     * @param resultLinkUsers
     * @return
     */
    List<Integer> viewNumberOfHandshakes(Map<String, String> resultLinkUsers) {
        List<Integer> calcCountHandshakes = new ArrayList<>();
        for (int a = 1; a <= resultLinkUsers.size() - 1; a++) {
            calcCountHandshakes.add(a);
        }
        return calcCountHandshakes;
    }

    /**
     * Метод, в котором ищем кратчайшую связь и записываем в minEntry
     *
     * @param countHandshakesResult
     * @return minEntry - возвращается минимальная связь
     */
    private Map.Entry<Long, List<Long>> searchMinRelation(Map<Long, List<Long>> countHandshakesResult) {
        Map.Entry<Long, List<Long>> minEntry = null;
        for (Map.Entry<Long, List<Long>> entry : countHandshakesResult.entrySet()) {
            if (minEntry == null || entry.getValue().size() <= minEntry.getValue().size()) {
                minEntry = entry;
            }
        }
        return minEntry;
    }

    /**
     * Метод, собирающий цепочку связей для визуализации на странице в виде иконок
     *
     * @param usersIds
     * @param currentUserId
     * @param minEntry
     * @return
     */
    Map<String, String> createLinksVisual(List<Long> usersIds, Long
            currentUserId, Map.Entry<Long, List<Long>> minEntry) {
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

// TODO Допилить через рекурсию
// TODO В "modelMap.put("countHandshakes", resultLinkUsers.size())"
//int countInitialUser = countInitialUser(currentUserId, userIdLeftColumnList);
// Осуществляем рекурсивный поиск по колонкам и помещаем все варианты в мапу
//boolean isFirstPass = true;
//boolean isFirstIn = true;
//List<Long> value = new ArrayList<>(); // Храним отдельно каждую цепочку и потом сохраняем в mapUsers
//Map<Long, List<Long>> mapUsers = new HashMap<>(); // Собираем все совпадения
//int stopCycle = 0; // Нужна, если нет ни одного совпадения - выходим из рекурсии
//int countInitialUserNext = 0;
//boolean isEnd = false;
//Long key = 0L;

/*    private Map<Long, List<Long>> searchWantedUser123(List<Long> value, Long key, Map<Long, List<Long>> mapUsers, boolean isFirstPass, int countInitialUserNext, int countInitialUser, Long userIdNextLink, Long initialUser, Long wantedUser, List<Long> userIdLeftColumnList, List<Long> userIdRightColumnList) {

        firstCycle:
        for (int i = 0; i < userIdLeftColumnList.size(); i++) {
            if (isFirstPass) {
                if (countInitialUserNext < countInitialUser) {
                    if (initialUser == userIdLeftColumnList.get(i)) {
                        userIdNextLink = userIdRightColumnList.get(i);
                        countInitialUserNext = countInitialUserNext + 1;
                        value = new ArrayList<>();
                        key++;
                        value.add(userIdNextLink);
                        if (value.size() > 6) {
                            value = new ArrayList<>();
                            continue firstCycle;
                        }
                        if (wantedUser == userIdNextLink) {
                            mapUsers.put(key, value);
                            value = new ArrayList<>();
                            break firstCycle;
                        }
                        isFirstPass = false;

                    }

                    if (!isFirstPass) {
                        secondCycle:
                        for (int j = 0; j < userIdLeftColumnList.size(); j++) {
                            if (userIdNextLink == userIdLeftColumnList.get(j)) {
                                userIdNextLink = userIdRightColumnList.get(j);
                                key++;
                                value.add(userIdNextLink);
                                if (value.size() > 6) {
                                    value = new ArrayList<>();
                                    isFirstPass = true;
                                    continue firstCycle;
                                }
                                if (wantedUser == userIdNextLink) {
                                    mapUsers.put(key, value);
                                    value = new ArrayList<>();
                                    isFirstPass = true;
                                    continue firstCycle;
                                }
                                searchWantedUser123(value, key, mapUsers, isFirstPass, countInitialUserNext, countInitialUser, userIdNextLink, initialUser, wantedUser, userIdLeftColumnList, userIdRightColumnList);
                            }
                        }
                    }
                }
            }
        }
        return mapUsers;
    }*/

 /*   private Map<Long, List<Long>> searchWantedUser(boolean isEnd, int countInitialUser, int countInitialUserNext, Long initialUser, Long wantedUser, Long userIdNextLink, List<Long> userIdLeftColumnList, List<Long> userIdRightColumnList, boolean isFirstPass, List<Long> value, Map<Long, List<Long>> mapUsers, int stopCycle) {

        Long key = 0L;
        boolean countMatched = false;

        firstCycle:
        for (int i = 0; i < userIdLeftColumnList.size(); i++) {
            if (isFirstPass) {
                if (initialUser == userIdLeftColumnList.get(i)) {
                    userIdNextLink = userIdRightColumnList.get(i);
                    key++;
                    countInitialUserNext = countInitialUserNext + 1;
                    System.out.println("countInitialUserNext " + countInitialUserNext);
                    System.out.println("initialUser 1: " + initialUser);
                    System.out.println("userIdNextLink 1: " + userIdNextLink);
                    value = new ArrayList<>();
                    value.add(userIdNextLink);
                    if (wantedUser == userIdNextLink) {

                        mapUsers.put(key, value);
                        value = new ArrayList<>();
                        continue firstCycle;
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
                        System.out.println("userIdNextLink 2: " + userIdNextLink);
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
                            continue firstCycle;
                        }
                    }
                   // searchWantedUser(countInitialUser, countInitialUserNext, initialUser, wantedUser, userIdNextLink, userIdLeftColumnList, userIdRightColumnList, isFirstPass, value, mapUsers, stopCycle);
                    if (countMatched) {
                        stopCycle = stopCycle + 1;
                        if (stopCycle > LIMIT_SIZE_HANDSHAKE) {
                            continue firstCycle;
                            //return mapUsers;
                        }
                        if(countInitialUserNext <= countInitialUser) {
                        searchWantedUser(isEnd, countInitialUser, countInitialUserNext, initialUser, wantedUser, userIdNextLink, userIdLeftColumnList, userIdRightColumnList, isFirstPass, value, mapUsers, stopCycle);
                        }
                        else {
                            return mapUsers;
                        }

                    }
                    if ((j == userIdLeftColumnList.size() - 1) && !countMatched) {
                        isFirstPass = true;
                        countMatched = false;
                        i++;
                        continue firstCycle;
                    }
                }
            }
        }
        return mapUsers;
    }*/

   /* private Map<Long, List<Long>> searchWantedUser(boolean isFirstPass, int countInitialUserNext, int countInitialUser, Long initialUser, Long wantedUser, Long userIdNextLink, List<Long> userIdLeftColumnList, List<Long> userIdRightColumnList, Map<Long, List<Long>> mapUsers) {
        // userIdNextLink = initialUser;
*//*        int countInitialUser = 0;
        int countIteration = 0;*//*
        //System.out.println("userIdNextLink 1: " + userIdNextLink);
        //System.out.println("countInitialUserNext 1: " + countInitialUserNext);
        //System.out.println("countInitialUser " + countInitialUser);

        firstCycle:
        for (int i = 0; i < userIdLeftColumnList.size(); i++) {
            if (isFirstPass) {
                if (countInitialUserNext > countInitialUser) {
                    break;
                }
                if (initialUser == userIdLeftColumnList.get(i)) {
                    userIdNextLink = userIdRightColumnList.get(i);
                    countInitialUserNext++;
                    System.out.println("countInitialUserNext " + countInitialUserNext);
                    System.out.println("initialUser: " + initialUser);
                    isFirstPass = false;
                }
            }
            if (!isFirstPass) {
                if (countInitialUserNext > countInitialUser) {
                    break;
                }
                for (int j = 0; j < userIdLeftColumnList.size(); j++) {
                    if (userIdNextLink == userIdLeftColumnList.get(j)) {
                        userIdNextLink = userIdRightColumnList.get(j);
                        System.out.println("userIdNextLink 2: " + userIdNextLink);
                        searchWantedUser(isFirstPass, countInitialUserNext, countInitialUser, initialUser, wantedUser, userIdNextLink, userIdLeftColumnList, userIdRightColumnList, mapUsers);
                        //return mapUsers;
                        isFirstPass = true;
                    }
                }
            }
        }
        return mapUsers;
    }*/

 /*   private Map<Long, List<Long>> searchWantedUser(Long initialUser, Long wantedUser, Long userIdNextLink, List<Long> userIdLeftColumnList, List<Long> userIdRightColumnList, boolean isFirstPass, List<Long> value, Map<Long, List<Long>> mapUsers, int stopCycle) {

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
    }*/
