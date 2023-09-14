package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.entity.Dialogs;
import run.itlife.entity.User;
import run.itlife.repository.DialogsRepository;
import run.itlife.repository.UserRepository;
import run.itlife.utils.SecurityUtils;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Service
@Transactional
public class DialogsServiceImpl implements DialogsService {

    private final DialogsRepository dialogsRepository;
    private final UserRepository userRepository;

    @Autowired
    public DialogsServiceImpl(DialogsRepository dialogsRepository, UserRepository userRepository) {
        this.dialogsRepository = dialogsRepository;
        this.userRepository = userRepository;
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
    public List<Dialogs> findDialogsByUsername(String username) {
        List<Dialogs> dialogs = new ArrayList<>();
        dialogs = dialogsRepository.findDialogsByUsername(username);
        /*for (Dialogs d : dialogs) { //TODO Попробовать выдернуть username, photo и ФИО собеседника и вывод его в Диалогах
            d.getUsers();
            System.out.println("Users: " + d.getUsers());
            for (User d1: d.getUsers()) {
                String username1 = d1.getUsername(); //выводится текущий и собеседник
                String userPhoto = d1.getPhoto();
                System.out.println(d1.getUsername());
                System.out.println(d1.getPhoto());

            }
        }*/
        /////////////////////////////////////////// TODO Допилить вывод фото и логинов
        /*Long dialogIdByUsers = dialogsService.getDialogIdByUsers(username,usernameCompanion);
        List<String> usersOwner = messagesService.findUsersByDialogId(dialogIdByUsers);
        // выводим в заголовке фото и имя собеседника
        String userDialogPhoto = messagesService.getUserPhotoByUsername(u);
        modelMap.put("userDialogName", u);
        modelMap.put("userDialogPhoto", userDialogPhoto);*/
        /////////////////////////////////////////////
        return dialogs;
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

}
