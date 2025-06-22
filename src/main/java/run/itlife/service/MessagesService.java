package run.itlife.service;

import run.itlife.entity.Messages;

import java.util.List;

public interface MessagesService {
    List<Messages> findAllMessages();
    void createMessage(Messages messages, Long dialogId, String file);
    List<Messages> findMessagesByDialogId(Long dialogId);
    Long countMessagesInDialog(Long dialogId);
    List<String> findUsersInDialog(Long dialogId);
    String findUserPhotoByUsername(String username);
    String findUserEmailByUsername(String username);
    String findUserGoogleByUsername(String username);
    List<String> findUsersByDialogId(Long dialogId);
    void isReadingMessage(long dialogId, String username);
}