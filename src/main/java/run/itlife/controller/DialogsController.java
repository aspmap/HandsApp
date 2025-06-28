package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import run.itlife.entity.Dialogs;
import run.itlife.service.DialogsService;
import run.itlife.service.MessagesService;
import run.itlife.utils.CommonsParams;

import java.util.List;

@Controller
@RequestMapping("/dialogs")
public class DialogsController {
    private final DialogsService dialogsService;
    private final MessagesService messagesService;
    @Autowired
    CommonsParams commonsParams;

    @Autowired
    public DialogsController(DialogsService dialogsService, MessagesService messagesService) {
        this.dialogsService = dialogsService;
        this.messagesService = messagesService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findDialogs(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("dialogs", dialogsService.findDialogs(username));
        modelMap.put("unreadMessages", dialogsService.findUnreadDialogs(username));
        modelMap.put("lastDateMessageOfDialog", dialogsService.findDialogsByUsername(username));
        commonsParams.setCommonParams(modelMap);
        return "dialogs/dialogs";
    }

    @GetMapping("/{usernameCompanion}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createDialog(Dialogs dialogs, ModelMap modelMap, @PathVariable String usernameCompanion) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        byte countDublicatesDialog = dialogsService.checkDuplicateDialogues(username, usernameCompanion);
        if (countDublicatesDialog == 0) {
            dialogsService.createDialog(dialogs, usernameCompanion);
        }
        Long dialogIdByUsers = dialogsService.findDialogIdByUsers(username, usernameCompanion);
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
                    String userDialogPhoto = messagesService.findUserPhotoByUsername(u);
                    String userDialogEmail = messagesService.findUserEmailByUsername(u);
                    String userDialogGoogle = messagesService.findUserGoogleByUsername(u);
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
        modelMap.put("dialog", dialogsService.findDialogById(dialogIdByUsers));
        return "dialogs/messages";
    }

    @GetMapping("/update_count_dialogs")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String updateCountDialogs(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("unreadMessagesTotal", dialogsService.findUnreadDialogs(username).size());
        return "fragments/unread-dialogs-count";
    }
}
