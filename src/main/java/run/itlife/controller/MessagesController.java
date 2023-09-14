package run.itlife.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import run.itlife.entity.Messages;
import run.itlife.service.DialogsService;
import run.itlife.service.MessagesService;
import run.itlife.service.UserService;

import java.util.List;

@Controller
public class MessagesController {

    private final UserService userService;
    private final MessagesService messagesService;
    private final DialogsService dialogsService;

    public MessagesController(UserService userService, MessagesService messagesService, DialogsService dialogsService) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.dialogsService = dialogsService;
    }

    @GetMapping("/messages/{dialogId}")
    @PreAuthorize("hasRole('USER')")
    public String view_messages(ModelMap modelMap, @PathVariable Long dialogId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //ищем участников диалога
        List<String> usersOwner = messagesService.findUsersByDialogId(dialogId);

        //выводим сообщения участников
        if (usersOwner.contains(username)) {
            modelMap.put("messages", messagesService.findMessagesByDialogId(dialogId));
        } else {
            setCommonParams(modelMap);
            return "messages-templates/404";
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
            setCommonParams(modelMap);
            return "messages-templates/404";
        }

        //выводим в заголовке количество сообщений
        modelMap.put("countMessagesInDialog", messagesService.countMessagesInDialog(dialogId));

        //получаем ID текущего диалога для отправки комментария
        modelMap.put("dialog", dialogsService.findById(dialogId));

        setCommonParams(modelMap);
        return "dialogs/messages";
    }

    @PostMapping("messages/create/{dialogId}")
    @PreAuthorize("hasRole('USER')")
    public String create(Messages messages, @PathVariable Long dialogId) {
        messagesService.create(messages, dialogId);
        return "redirect:/messages/" + dialogId;
    }

    private void setCommonParams(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
    }

}