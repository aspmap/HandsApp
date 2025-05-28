package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.entity.Dialogs;
import run.itlife.entity.User;
import run.itlife.repository.DialogsRepository;
import run.itlife.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class DialogsServiceImpl implements DialogsService {
    private final DialogsRepository dialogsRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public DialogsServiceImpl(DialogsRepository dialogsRepository, UserRepository userRepository, UserService userService) {
        this.dialogsRepository = dialogsRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void create(Dialogs dialogs, String usernameCompanion) {
        //String username = SecurityUtils.getCurrentUserDetails().getUsername();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        User userCompanion = userRepository.findByUsername(usernameCompanion).orElseThrow(() -> new UsernameNotFoundException(usernameCompanion));
        dialogs.setCreatedAt(LocalDateTime.now());
        if(user.getIsGoogle() == true) {
            dialogs.setNameDialog("Диалог " + user.getEmail() + " и " + userCompanion);
        }
        else {
            dialogs.setNameDialog("Диалог " + username + " и " + userCompanion);
        }
        dialogsRepository.save(dialogs);
        Set<User> users = new HashSet<>();
        users.add(user);
        users.add(userCompanion);
        dialogs.setUsers(users);
        dialogsRepository.save(dialogs);
    }

    @Override
    public Map<Long, User> findDialogs(String username) {
        Map<Long, User> dialogs = new HashMap<>();
        ArrayList<Long> dialogsId = findDialogsIdByUsername(username);

        for (int i = 0; i < dialogsId.size(); i++) {
            ArrayList<User> usernames = new ArrayList<>();
            usernames = userService.findUsersByDialogId(dialogsId.get(i));
            for (int j = 0; j < usernames.size(); j++) {
                Integer isShowDialog = showDialog(username, dialogsId.get(i));
                if (!usernames.get(j).getUsername().equals(username) && isShowDialog > 0) {
                    dialogs.put(dialogsId.get(i), usernames.get(j));
                }
            }
        }
        return dialogs;
    }

    @Override
    public Integer showDialog(String username, Long dialogId) {
        return dialogsRepository.showDialog(username, dialogId);
    }

    @Override
    public Dialogs findById(long id) {
        Dialogs dialog = dialogsRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        dialog.getNameDialog().length();
        return dialog;
    }

    @Override
    public byte checkDuplicateDialogues(String username1, String username2) {
        return dialogsRepository.checkDuplicateDialogues(username1, username2);
    }

    @Override
    public Long getDialogIdByUsers(String username1, String username2) {
        return dialogsRepository.getDialogIdByUsers(username1, username2);
    }

    @Override
    public ArrayList<Long> findDialogsIdByUsername(String username) {
        return dialogsRepository.findDialogsIdByUsername(username);
    }

}
