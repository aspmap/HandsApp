package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.service.HandshakeService;
import run.itlife.service.UserService;

import java.util.*;

import static run.itlife.messages.ErrorMessages.ERROR;
import static run.itlife.utils.SecurityUtils.getCurrentUserDetails;

@Controller
public class HandshakeController {

    private final HandshakeService handshakeService;
    private final UserService userService;
    //final short LIMIT_SIZE_HANDSHAKE = 6;
    private static final Logger log = LoggerFactory.getLogger(HandshakeController.class);

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
        wantedUsername = wantedUsername.toLowerCase();
        setCommonParams(modelMap);

        // Получаем Id текущего пользователя
        Long currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().longValue();
        //Long userIdNextLink = 0L;

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

                //++++ Собираем в очередь связи
                ArrayDeque<List<Long>> queue = getUsersLevels(currentUserId, userIdLeftColumnList, userIdRightColumnList);

                //++++ Выводим число рукопожатий
                int countHandshakes = countLinks(queue, wantedUserId);

                // Поиск связей
                //Map<Long, List<Long>> countHandshakesResult = searchWantedUser(countHandshakes, currentUserId, wantedUserId, userIdLeftColumnList, userIdRightColumnList);

                // В полученной мапе ищем кратчайшую связь и записываем в minEntry
                //Map.Entry<Long, List<Long>> minEntry = null;
                //minEntry = searchMinRelation(countHandshakesResult);

                // Собираем цепочку связей для визуализации на странице в виде иконок
                //Map<String, String> resultLinkUsers = new HashMap<>();

                //resultLinkUsers = createLinksVisual(currentUserId, minEntry);

                // Выводим число рукопожатий - преобразуем в Long. -1 - минусуем текущего пользователя
                //Long resultLinkUsersLong = Long.valueOf(resultLinkUsers.size() - 1);

                // Выводим номер рукопожатия для корректной работы условий на странице с результатами
                //List<Integer> viewNumberOfHandshake = viewNumberOfHandshakes(resultLinkUsers);
                //List<Integer> viewNumberOfHandshake = viewNumberOfHandshakesNew(countHandshakes);

                // Выводим число рукопожатий
                modelMap.put("countHandshakes", countHandshakes);

                // Выводим данные искомого пользователя
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                modelMap.put("wantedUsername", wantedUsername);
                modelMap.put("wantedUserinfo", userService.findByUsername(wantedUsername));
                modelMap.put("userinfo_check", checkUsername(username));


                // Собираем цепочку связей для визуализации на странице в виде иконок
                //modelMap.put("LinkUsersTree", resultLinkUsers);

                // Выводим номер рукопожатия
                //modelMap.put("viewNumberOfHandshake", viewNumberOfHandshake);

                return "handshakes/handshakes-results";
            } catch (Exception e) {
                log.error(ERROR + e);
                return "messages-templates/usernotfound";
            }
        }
    }

    /**
     * Метод, собирающий связи исходного пользователя с подписчиками до шестого уровня
     * @param initialUser
     * @param userIdLeftColumnList
     * @param userIdRightColumnList
     * @return возвращаются связи исходного пользователя с подписчиками до шестого уровня
     */
    private ArrayDeque<List<Long>> getUsersLevels(Long initialUser, List<Long> userIdLeftColumnList, List<Long> userIdRightColumnList) {

        List<Long> friends1 = new ArrayList<>();
        List<Long> friends2 = new ArrayList<>();
        List<Long> friends3 = new ArrayList<>();
        List<Long> friends4 = new ArrayList<>();
        List<Long> friends5 = new ArrayList<>();
        List<Long> friends6 = new ArrayList<>();

        for (int i = 0; i < userIdLeftColumnList.size(); i++) {
            if (initialUser == userIdLeftColumnList.get(i)) {
                friends1.add(userIdRightColumnList.get(i));

                for (int j = 0; j < userIdLeftColumnList.size(); j++) {
                    if (friends1.get((friends1.size() - 1)) == userIdLeftColumnList.get(j)) {
                        friends2.add(userIdRightColumnList.get(j));

                        for (int k = 0; k < userIdLeftColumnList.size(); k++) {
                            if (friends2.get((friends2.size() - 1)) == userIdLeftColumnList.get(k)) {
                                friends3.add(userIdRightColumnList.get(k));

                                for (int m = 0; m < userIdLeftColumnList.size(); m++) {
                                    if (friends3.get((friends3.size() - 1)) == userIdLeftColumnList.get(m)) {
                                        friends4.add(userIdRightColumnList.get(m));

                                        for (int n = 0; n < userIdLeftColumnList.size(); n++) {
                                            if (friends4.get((friends4.size() - 1)) == userIdLeftColumnList.get(n)) {
                                                friends5.add(userIdRightColumnList.get(n));

                                                for (int s = 0; s < userIdLeftColumnList.size(); s++) {
                                                    if (friends5.get((friends5.size() - 1)) == userIdLeftColumnList.get(s)) {
                                                        friends6.add(userIdRightColumnList.get(s));
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
        ArrayDeque<List<Long>> queue = new ArrayDeque<>();
        queue.add(friends1);
        queue.add(friends2);
        queue.add(friends3);
        queue.add(friends4);
        queue.add(friends5);
        queue.add(friends6);
        return queue;
    }

    /**
     * Метод, считающий количество рукопожатий
     * @param queue      массив данных всех шести уровней
     * @param wantedUser искомый пользователь
     * @return возвращается число рукопожатий
     */
    private int countLinks(ArrayDeque<List<Long>> queue, Long wantedUser) {
        int i = 0;
        while (!queue.isEmpty()) {
            i++;
            List<Long> user = queue.pollFirst();
            if (user.contains(wantedUser)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Помещает в очередь все уровни (Метод пока не используется, находится в разработке на перспективу, для вывода полной цепочки на странице)
     * @param initialUser
     * @param userIdLeftColumnList
     * @param userIdRightColumnList
     * @return
     */
    private Map<Long, List<Long>> searchWantedUser(int countHandshakes, Long initialUser, Long wantedUser, List<Long> userIdLeftColumnList, List<Long> userIdRightColumnList) {
        List<Long> friends1 = new ArrayList<>();
        List<Long> friends2 = new ArrayList<>();
        List<Long> friends3 = new ArrayList<>();
        List<Long> friends4 = new ArrayList<>();
        List<Long> friends5 = new ArrayList<>();
        List<Long> friends6 = new ArrayList<>();
        List<Long> searched = new ArrayList<>();
        Map<Long, List<Long>> mapUsers = new HashMap<>();
        List<Long> users = new ArrayList<>();
        Long p = 0L;

        if (countHandshakes >= 1) {
            for (int i = 0; i < userIdLeftColumnList.size(); i++) {
                if (!searched.contains(userIdRightColumnList.get(i))) {
                    if (initialUser == userIdLeftColumnList.get(i)) {
                        users = new ArrayList<>();
                        searched = new ArrayList<>();
                        friends1.add(userIdRightColumnList.get(i));
                        searched.add(userIdLeftColumnList.get(i));
                        users.add(friends1.get(getIndexOfLastElement(friends1)));
                        if (friends1.get(getIndexOfLastElement(friends1)) == wantedUser) {
                            mapUsers.put(p, users);
                            return mapUsers;
                        }

                        if (countHandshakes >= 2) {
                            for (int j = 0; j < userIdLeftColumnList.size(); j++) {
                                if (!searched.contains(userIdRightColumnList.get(j))) {
                                    if (friends1.get(getIndexOfLastElement(friends1)) == userIdLeftColumnList.get(j)) {
                                        friends2.add(userIdRightColumnList.get(j));
                                        searched.add(userIdLeftColumnList.get(j));
                                        users.add(friends2.get(getIndexOfLastElement(friends2)));
                                        if (friends2.get(getIndexOfLastElement(friends2)) == wantedUser) {
                                            mapUsers.put(p, users);
                                            return mapUsers;
                                        }

                                        if (countHandshakes >= 3) {
                                            for (int k = 0; k < userIdLeftColumnList.size(); k++) {
                                                if (!searched.contains(userIdRightColumnList.get(k))) {
                                                    if (friends2.get(getIndexOfLastElement(friends2)) == userIdLeftColumnList.get(k)) {
                                                        friends3.add(userIdRightColumnList.get(k));
                                                        searched.add(userIdLeftColumnList.get(k));
                                                        users.add(friends3.get(getIndexOfLastElement(friends3)));
                                                        if (friends3.get(getIndexOfLastElement(friends3)) == wantedUser) {
                                                            mapUsers.put(p, users);
                                                            return mapUsers;
                                                        }

                                                        if (countHandshakes >= 4) {
                                                            for (int m = 0; m < userIdLeftColumnList.size(); m++) {
                                                                if (!searched.contains(userIdRightColumnList.get(m))) {
                                                                    if (friends3.get(getIndexOfLastElement(friends3)) == userIdLeftColumnList.get(m)) {
                                                                        friends4.add(userIdRightColumnList.get(m));
                                                                        searched.add(userIdLeftColumnList.get(m));
                                                                        users.add(friends4.get(getIndexOfLastElement(friends4)));
                                                                        if (friends4.get(getIndexOfLastElement(friends4)) == wantedUser) {
                                                                            mapUsers.put(p, users);
                                                                            return mapUsers;
                                                                        }

                                                                        if (countHandshakes >= 5) {
                                                                            for (int n = 0; n < userIdLeftColumnList.size(); n++) {
                                                                                if (!searched.contains(userIdRightColumnList.get(n))) {
                                                                                    if (friends4.get(getIndexOfLastElement(friends4)) == userIdLeftColumnList.get(n)) {
                                                                                        friends5.add(userIdRightColumnList.get(n));
                                                                                        searched.add(userIdLeftColumnList.get(n));
                                                                                        users.add(friends5.get(getIndexOfLastElement(friends5)));
                                                                                        if (friends5.get(getIndexOfLastElement(friends5)) == wantedUser) {
                                                                                            mapUsers.put(p, users);
                                                                                            return mapUsers;
                                                                                        }

                                                                                        if (countHandshakes == 6) {
                                                                                            for (int s = 0; s < userIdLeftColumnList.size(); s++) {
                                                                                                if (!searched.contains(userIdRightColumnList.get(s))) {
                                                                                                    if (friends5.get(getIndexOfLastElement(friends5)) == userIdLeftColumnList.get(s)) {
                                                                                                        friends6.add(userIdRightColumnList.get(s));
                                                                                                        searched.add(userIdLeftColumnList.get(s));
                                                                                                        users.add(friends6.get(getIndexOfLastElement(friends6)));
                                                                                                        if (friends6.get(getIndexOfLastElement(friends6)) == wantedUser) {
                                                                                                            mapUsers.put(p, users);
                                                                                                            return mapUsers;
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
        }
        return mapUsers;
    }

    /**
     * Метод, возвращающий индекс последнего элемента
     * @param object объект типа List
     * @return возвращается индекс последнего элемента
     */
    private int getIndexOfLastElement(List<Long> object) {
        return (object.size() - 1);
    }

    /**
     * Метод, считающий количество подписок у текущего пользователя
     * @param initialUser          текущий пользователь
     * @param userIdLeftColumnList список пользователей
     * @return возвращается количество подписок у текущего пользователя
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
     * Метод, собирающий цепочку связей для визуализации на странице в виде иконок
     * @param currentUserId
     * @param minEntry
     * @return
     */
    Map<String, String> createLinksVisual(Long currentUserId, Map.Entry<Long, List<Long>> minEntry) {
        List<Long> usersIds = new ArrayList<>();
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

    /**
     * Метод, собирающий цепочку связей для визуализации на странице в виде иконок
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
     * Метод, выводящий номер связи
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
     * Метод, выводящий номер связи
     * @param countHandshakes
     * @return
     */
    List<Integer> viewNumberOfHandshakesNew(int countHandshakes) {
        List<Integer> calcCountHandshakes = new ArrayList<>();
        for (int i = 1; i <= countHandshakes; i++) {
            calcCountHandshakes.add(i);
        }
        return calcCountHandshakes;
    }

    /**
     * Метод, в котором ищем кратчайшую связь и записываем в minEntry
     * @param countHandshakesResult
     * @return возвращается минимальная связь
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
     * Общие методы для отображения информации на странице
     * @param modelMap
     */
    private void setCommonParams(ModelMap modelMap) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
    }

    private String checkUsername(String username) {
        if(userService.findByUsername(username).getIsGoogle() == true) {
            username = userService.findByUsername(username).getEmail();
        }
        return username;
    }

}