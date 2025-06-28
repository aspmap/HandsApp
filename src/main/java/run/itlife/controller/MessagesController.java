package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import run.itlife.entity.Messages;
import run.itlife.service.DialogsService;
import run.itlife.service.MessagesService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

import javax.servlet.ServletContext;
import java.util.List;

import static run.itlife.utils.Properties.ErrorMessages.ERROR;
import static run.itlife.utils.Properties.ErrorMessages.NOT_PUBLISH_MESSAGE;
import static run.itlife.utils.Properties.Paths.SEPARATOR;

@Controller
@RequestMapping("/messages")
public class MessagesController {
    private final MessagesService messagesService;
    private final DialogsService dialogsService;
    private final ServletContext context;
    @Autowired
    CommonsParams commonsParams;
    private static final Logger log = LoggerFactory.getLogger(MessagesController.class);

    public MessagesController(MessagesService messagesService, DialogsService dialogsService, ServletContext context) {
        this.messagesService = messagesService;
        this.dialogsService = dialogsService;
        this.context = context;
    }

    @GetMapping("/{dialogId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findMessagesByDialogId(ModelMap modelMap, @PathVariable Long dialogId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //ищем участников диалога
        List<String> usersOwner = messagesService.findUsersByDialogId(dialogId);

        //выводим сообщения участников
        if (usersOwner.contains(username)) {
            modelMap.put("messages", messagesService.findMessagesByDialogId(dialogId));
        } else {
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/404";
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
                    messagesService.isReadingMessage(dialogId, u);
                }
            }
        } else {
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/404";
        }

        //выводим в заголовке количество сообщений
        modelMap.put("countMessagesInDialog", messagesService.countMessagesInDialog(dialogId));

        //получаем ID текущего диалога для отправки комментария
        modelMap.put("dialog", dialogsService.findDialogById(dialogId));

        commonsParams.setCommonParams(modelMap);
        return "dialogs/messages";
    }

    @PostMapping("/create/{dialogId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createMessage(ModelMap modelMap, Messages messages, @PathVariable Long dialogId, @RequestParam("file") String file) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SaveFile sf = new SaveFile();
        String filename = "1";
        try {
            if (file != null && !file.equals("")) {
                filename = sf.saveFileInDialog(username, context, file);
            }
            if (filename.equals("0")) {
                commonsParams.setCommonParams(modelMap);
                modelMap.put("dialogId", dialogId);
                return "messages-templates" + SEPARATOR + "errorFileSizeMessages";
            }
            if (filename.equals("1") && messages.getMessageText().equals("")) {
                return "redirect:/messages/" + dialogId;
            }
            messagesService.createMessage(messages, dialogId, filename);
            return "redirect:/messages/" + dialogId;
        } catch (Exception e) {
            log.error(ERROR + NOT_PUBLISH_MESSAGE);
            return "messages-templates" + SEPARATOR + "errorFileSizeMessages";
        }
    }
}