package run.itlife.service;

import run.itlife.entity.Dialogs;
import run.itlife.entity.User;

import java.util.ArrayList;
import java.util.Map;

public interface DialogsService {
    void create(Dialogs dialogs, String usernameCompanion);
    Dialogs findById(long id);
    byte checkDuplicateDialogues(String username1, String username2);
    Long getDialogIdByUsers(String username1, String username2);
    ArrayList<Long> findDialogsIdByUsername(String username);
    Map<Long, User> findDialogs(String username);
    Integer showDialog(Long dialogId);
    Map<Integer, Integer> findUnreadDialogs(String username);
    Map<String, Integer> countUnreadMessagesInDialog(Long dialogId, String username);
}