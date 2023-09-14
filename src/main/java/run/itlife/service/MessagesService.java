package run.itlife.service;

import run.itlife.entity.Messages;
import java.util.List;

public interface MessagesService {

    List<Messages> listAllMessages();
    void create(Messages messages, Long dialogId);
    List<Messages> findMessagesByDialogId(Long dialogId);
    Long countMessagesInDialog(Long dialogId);
    List<String> findUsersInDialog(Long dialogId);
    List<String> getDialogIdByUsersOwner(String username1, String username2);
    String getUserPhotoByUsername(String username);
    String getUserEmailByUsername(String username);
    String getUserGoogleByUsername(String username);
    List<String> findUsersByDialogId(Long dialogId);

}