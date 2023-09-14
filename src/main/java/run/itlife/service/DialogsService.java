package run.itlife.service;

import run.itlife.entity.Dialogs;
import java.util.List;

public interface DialogsService {

    void create(Dialogs dialogs, String usernameCompanion);
    List<Dialogs> findDialogsByUsername(String username);
    Dialogs findById(long id);
    byte checkDuplicateDialogues(String username1, String username2);
    Long getDialogIdByUsers(String username1, String username2);

}