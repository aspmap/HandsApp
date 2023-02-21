package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import run.itlife.entity.Dialogs;
import run.itlife.service.DialogsService;
import run.itlife.service.MessagesService;
import run.itlife.service.UserService;

import java.util.List;

@Controller
public class DialogsController {

    private final UserService userService;
    private final DialogsService dialogsService;
    private final MessagesService messagesService;

    @Autowired
    public DialogsController(UserService userService, DialogsService dialogsService, MessagesService messagesService) {
        this.userService = userService;
        this.dialogsService = dialogsService;
        this.messagesService = messagesService;
    }

    @GetMapping("/dialogs")
    @PreAuthorize("hasRole('USER')")
    public String index(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("dialogs", dialogsService.findDialogsByUsername(username));
        setCommonParams(modelMap);
        return "dialogs/dialogs";
    }

    @GetMapping("/dialogs/{usernameCompanion}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String create_dialog(Dialogs dialogs, ModelMap modelMap, @PathVariable String usernameCompanion) {
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        byte countDublicatesDialog = dialogsService.checkDuplicateDialogues(username, usernameCompanion);
        if (countDublicatesDialog == 0) {
            dialogsService.create(dialogs, usernameCompanion);
        }
        Long dialogIdByUsers = dialogsService.getDialogIdByUsers(username, usernameCompanion);
        List<String> usersOwner = messagesService.findUsersByDialogId(dialogIdByUsers);
        if (messagesService.countMessagesInDialog(dialogIdByUsers) != 0) {
            if (usersOwner.contains(username)) {
                modelMap.put("messages", messagesService.findMessagesByDialogId(dialogIdByUsers));
            }
        }

        // выводим в заголовке фото и имя собеседника
        if (usersOwner.size() != 0) {
            for (String u : usersOwner) {
                if (!u.equals(username)) {
                    String userDialogPhoto = messagesService.getUserPhotoByUsername(u);
                    String userDialogEmail = messagesService.getUserEmailByUsername(u);
                    String userDialogGoogle = messagesService.getUserGoogleByUsername(u);
                    modelMap.put("userDialogName", u);
                    modelMap.put("userDialogPhoto", userDialogPhoto);
                    modelMap.put("userDialogEmail", userDialogEmail);
                    modelMap.put("userDialogGoogle", userDialogGoogle);
                }
            }
        } else {
            modelMap.put("userDialogName", "Диалог удален или не существует");
        }
        modelMap.put("countMessagesInDialog", messagesService.countMessagesInDialog(dialogIdByUsers));
        modelMap.put("dialog", dialogsService.findById(dialogIdByUsers));
        return "dialogs/messages";
    }

    private void setCommonParams(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
    }

}
