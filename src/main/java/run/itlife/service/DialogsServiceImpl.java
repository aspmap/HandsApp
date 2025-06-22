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
    public void createDialog(Dialogs dialogs, String usernameCompanion) {
        //String username = SecurityUtils.getCurrentUserDetails().getUsername();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        User userCompanion = userRepository.findByUsername(usernameCompanion).orElseThrow(() -> new UsernameNotFoundException(usernameCompanion));
        dialogs.setCreatedAt(LocalDateTime.now());
        if (user.getIsGoogle() == true) {
            dialogs.setNameDialog("Диалог " + user.getEmail() + " и " + userCompanion);
        } else {
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
        Map<Long, User> dialogs = new LinkedHashMap<>();
        ArrayList<Long> dialogsId = findDialogsIdByUsername(username);

        for (int i = 0; i < dialogsId.size(); i++) {
            ArrayList<User> usernames = new ArrayList<>();
            usernames = userService.findUsersByDialogId(dialogsId.get(i));
            for (int j = 0; j < usernames.size(); j++) {
                Integer isShowDialog = findCountDialog(dialogsId.get(i));
                if (!usernames.get(j).getUsername().equals(username) && isShowDialog > 0) {
                    dialogs.put(dialogsId.get(i), usernames.get(j));
                }
            }
        }
        return dialogs;
    }

    @Override
    public Map<Integer, Integer> findUnreadDialogs(String username) {
        Map<Long, User> dialogs = findDialogs(username);
        Map<Integer, Integer> unreadDialogs = new HashMap<>();

        for (Map.Entry<Long, User> entry : dialogs.entrySet()) {
            Map<String, Integer> unreadDialog = countUnreadMessagesInDialog(entry.getKey(), entry.getValue().getUsername());

            if (!unreadDialog.isEmpty()) {
                Integer dialogId = 0;
                Integer countMessages = 0;
                for (Map.Entry<String, Integer> entryDialog : unreadDialog.entrySet()) {
                    if (entryDialog.getKey().equals("dialog_id")) {
                        dialogId = entryDialog.getValue().intValue();
                    }
                    if (entryDialog.getKey().equals("count_messages")) {
                        countMessages = entryDialog.getValue().intValue();
                    }
                }
                if (countMessages != 0) {
                    unreadDialogs.put(dialogId, countMessages);
                }
            }
        }
        return unreadDialogs;
    }

    @Override
    public Integer findCountDialog(Long dialogId) {
        return dialogsRepository.findCountDialog(dialogId);
    }

    @Override
    public Dialogs findDialogById(long id) {
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
    public Long findDialogIdByUsers(String username1, String username2) {
        return dialogsRepository.findDialogIdByUsers(username1, username2);
    }

    @Override
    public ArrayList<Long> findDialogsIdByUsername(String username) {
        return dialogsRepository.findDialogsIdByUsername(username);
    }

    @Override
    public Map<String, Integer> countUnreadMessagesInDialog(Long dialogId, String username) {
        return dialogsRepository.countUnreadMessagesInDialog(dialogId, username);
    }

    @Override
    public ArrayList<Dialogs> findDialogsByUsername(String username) {
        return dialogsRepository.findDialogsByUsername(username);
    }
}
