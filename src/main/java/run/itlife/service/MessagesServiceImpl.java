package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.entity.Dialogs;
import run.itlife.entity.Messages;
import run.itlife.entity.User;
import run.itlife.repository.MessagesRepository;

import java.time.LocalDateTime;
import java.util.List;

import static run.itlife.enums.FileExtensions.PNG;

@Service
@Transactional
public class MessagesServiceImpl implements MessagesService {
    private final MessagesRepository messagesRepository;
    private final UserService userService;
    private final DialogsService dialogsService;

    @Autowired
    public MessagesServiceImpl(MessagesRepository messagesRepository, UserService userService, DialogsService dialogsService) {
        this.messagesRepository = messagesRepository;
        this.userService = userService;
        this.dialogsService = dialogsService;
    }

    @Override
    public List<Messages> findAllMessages() {
        List<Messages> messages =  messagesRepository.findAll(Sort.by("createdAt").descending());
        for (Messages m : messages) {
            m.getCreatedAt().getMonth();
        }
        return messages;
    }

    @Override
    public void createMessage(Messages messages, Long dialogId, String file) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userId = userService.findByUsername(username);
        Dialogs dialogIdCurrent = dialogsService.findDialogById(dialogId);
        dialogIdCurrent.setUpdatedAt(LocalDateTime.now());
        messages.setCreatedAt(LocalDateTime.now());
        messages.setUser(userId);
        messages.setDialogs(dialogIdCurrent);
        if (file != null && !file.equals("") && !file.equals("1")) {
            messages.setMessageFile(file);
            messages.setExtFile(PNG.getExtension());
        }
        messagesRepository.save(messages);
    }

    @Override
    public List<Messages> findMessagesByDialogId(Long dialogId) {
        return messagesRepository.findMessagesByDialogId(dialogId);

    }

    @Override
    public Long countMessagesInDialog(Long dialogId) {
        return messagesRepository.countMessagesInDialog(dialogId);
    }

    @Override
    public List<String> findUsersInDialog(Long dialogId) {
        return messagesRepository.findUsersInDialog(dialogId);
    }

    @Override
    public String findUserPhotoByUsername(String username) {
        return messagesRepository.findUserPhotoByUsername(username);
    }

    @Override
    public String findUserEmailByUsername(String username) {
        return messagesRepository.findUserEmailByUsername(username);
    }

    @Override
    public String findUserGoogleByUsername(String username) {
        return messagesRepository.findUserGoogleByUsername(username);
    }

    @Override
    public List<String> findUsersByDialogId(Long dialogId) {
        return messagesRepository.findUsersByDialogId(dialogId);
    }

    @Override
    public void isReadingMessage(long dialogId, String username) {
        messagesRepository.isReadingMessage(dialogId, username);
    }
}