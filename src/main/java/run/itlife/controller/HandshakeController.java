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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.entity.User;
import run.itlife.repository.DialogsRepository;
import run.itlife.service.DialogsService;
import run.itlife.service.HandshakeService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.Handshakes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static run.itlife.utils.Properties.ErrorMessages.*;


@Controller
@RequestMapping("/handshakes")
public class HandshakeController {
    private final HandshakeService handshakeService;
    private final UserService userService;
    private final DialogsService dialogsService;
    @Autowired
    CommonsParams commonsParams;
    private static final Logger log = LoggerFactory.getLogger(HandshakeController.class);
    private static final Byte START_LEVEL = 1;

    @Autowired
    public HandshakeController(HandshakeService handshakeService, UserService userService, DialogsService dialogsService) {
        this.handshakeService = handshakeService;
        this.userService = userService;
        this.dialogsService = dialogsService;
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findHandshakes(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "handshakes/handshakes-search";
    }

    @PostMapping("/results")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String getHandshakesResults(ModelMap modelMap, @RequestParam(required = false) String searchUsername) throws ExecutionException, InterruptedException {
        // Используем Executor и Future
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Future<String> page = executorService.submit(
                new Callable<>() {
                    final String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
                    final Integer currentUserId = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId().intValue();

                    @Override
                    public String call() {
                        String searchUsernameLowerCase = searchUsername.toLowerCase();
                        setCommonParamsSynchronized(modelMap, currentUsername);

                        if (searchUsernameLowerCase.equals("")) {
                            return "handshakes/handshakes-search";
                        } else {
                            try {
                                Integer searchUserId = userService.findByUsername(searchUsernameLowerCase).getUserId().intValue();
                                ArrayList<Integer> searched = new ArrayList<>();
                                Map<Integer, ArrayList<Integer>> usersGraph = new HashMap<>();
                                ArrayList<Integer> usersIdFirstLevel = handshakeService.findUsersId(currentUserId);
                                usersGraph.put(currentUserId, usersIdFirstLevel);
                                Handshakes handshakes = new Handshakes();
                                Byte level = START_LEVEL;
                                usersGraph = buildGraph(usersIdFirstLevel, usersGraph, searched, level);
                                handshakes.setStartPerson(currentUserId);
                                handshakes.setGraph(usersGraph);

                                ArrayList<Integer> path = handshakes.search(searchUserId);
                                Integer countHandshakes = handshakes.getSizePath();
                                ArrayList<User> visualView = createVisualView(path);

                                modelMap.put("countHandshakes", countHandshakes);
                                modelMap.put("pathOfUsers", path);
                                modelMap.put("searchUsername", searchUsernameLowerCase);
                                modelMap.put("searchUserinfo", userService.findByUsername(searchUsernameLowerCase));
                                modelMap.put("pathOfUsersVisual", visualView);

                                return "handshakes/handshakes-results";
                            } catch (Exception e) {
                                log.error(ERROR + HANDSHAKE_SEARCH_ERROR);
                                return "messages-templates/usernotfound";
                            }
                        }
                    }
                });
        executorService.shutdown();
        return page.get();
    }

    private Map<Integer, ArrayList<Integer>> buildGraph(ArrayList<Integer> usersIdLevel, Map<Integer, ArrayList<Integer>> usersGraph, ArrayList<Integer> searched, Byte level) {
        if (level > Handshakes.LIMIT_SIZE_HANDSHAKE) {
            return usersGraph;
        }
        for (int i = 0; i < usersIdLevel.size(); i++) {
            ArrayList<Integer> usersIdNextLevel = new ArrayList<>();
            if (!searched.contains(usersIdLevel.get(i))) {
                Integer usernameId = usersIdLevel.get(i);
                usersIdNextLevel = handshakeService.findUsersId(usernameId);
                usersGraph.put(usernameId, usersIdNextLevel);
                if (i == (usersIdLevel.size() - 1)) {
                    level++;
                }
                searched.add(usernameId);
            }
            buildGraph(usersIdNextLevel, usersGraph, searched, level);
        }
        return usersGraph;
    }

    private ArrayList<User> createVisualView(ArrayList<Integer> path) {
        List<String> usersId;
        ArrayList<User> view = new ArrayList<>();

        if (path != null) {
            for (int i = path.size() - 1; i >= 0; i--) {
                usersId = handshakeService.findUsersById(path.get(i));
                String[] findUserByIdLinkArray = usersId.toString().split(",");
                String[] linkUsernameSplit = findUserByIdLinkArray[0].split("\\u005b");
                String linkUsername = linkUsernameSplit[1];
                User user = userService.findByUsername(linkUsername);
                view.add(user);
            }
        } else {
            view.clear();
        }
        return view;
    }

    private void setCommonParamsSynchronized(ModelMap modelMap, String username) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.findUsersOnly());
        modelMap.put("usersOnlyKey", userService.findUsersOnlyKey(username));
        modelMap.put("unreadMessagesTotal", dialogsService.findUnreadDialogs(username).size());
    }
}