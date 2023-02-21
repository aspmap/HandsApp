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
    public List<Messages> listAllMessages() {
        List<Messages> messages =  messagesRepository.findAll(Sort.by("createdAt").descending());
        for (Messages m : messages) {
            m.getCreatedAt().getMonth();
        }
        return messages;
    }

    @Override
    public void create(Messages messages, Long dialogId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userId = userService.findByUsername(username);
        Dialogs dialogIdCurrent = dialogsService.findById(dialogId);
        messages.setCreatedAt(LocalDateTime.now());
        messages.setUser(userId);
        messages.setDialogs(dialogIdCurrent);
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
    public List<String> getDialogIdByUsersOwner(String username1, String username2) {
        return messagesRepository.getDialogIdByUsersOwner(username1, username2);
    }

    @Override
    public String getUserPhotoByUsername(String username) {
        return messagesRepository.getUserPhotoByUsername(username);
    }

    @Override
    public String getUserEmailByUsername(String username) {
        return messagesRepository.getUserEmailByUsername(username);
    }

    @Override
    public String getUserGoogleByUsername(String username) {
        return messagesRepository.getUserGoogleByUsername(username);
    }

    @Override
    public List<String> findUsersByDialogId(Long dialogId) {
        return messagesRepository.findUsersByDialogId(dialogId);
    }

}