package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Messages;
import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    @Query(value = "select * from messages m " +
            "left join dialogs d on d.dialog_id = m.dialog_id " +
            "where d.dialog_id = ? " +
            "order by m.created_at ", nativeQuery = true)
    List<Messages> findMessagesByDialogId(Long dialogId);

    @Query(value = "select distinct u.username from users u " +
            "left join user_dialog ud on u.user_id = ud.user_id " +
            "left join dialogs d on ud.dialog_id = d.dialog_id " +
            "left join dialogs d2 on d2.dialog_id = d.dialog_id " +
            "left join user_dialog ud2 on ud2.dialog_id = d2.dialog_id " +
            "left join users u2 on u2.user_id = ud2.user_id " +
            "where d.dialog_id = ? ", nativeQuery = true)
    List<String> findUsersByDialogId(Long dialogId);

    @Query(value = "select count(*) from messages m " +
            "left join dialogs d on d.dialog_id = m.dialog_id " +
            "where d.dialog_id = ? ", nativeQuery = true)
    Long countMessagesInDialog(Long dialogId);

    @Query(value = "select distinct u.username from messages m " +
    "left join dialogs d on d.dialog_id = m.dialog_id " +
    "left join user_dialog ud on d.dialog_id = ud.dialog_id " +
    "left join users u on u.user_id = ud.user_id " +
    "where d.dialog_id = ? ", nativeQuery = true)
    List<String> findUsersInDialog(Long dialogId);

    @Query(value = "select u2.username from users u " +
            "left join user_dialog ud on u.user_id = ud.user_id " +
            "left join dialogs d on ud.dialog_id = d.dialog_id " +
            "left join dialogs d2 on d2.dialog_id = d.dialog_id " +
            "left join user_dialog ud2 on ud2.dialog_id = d2.dialog_id " +
            "left join users u2 on u2.user_id = ud2.user_id " +
            "where u.username = ? and u2.username = ? ", nativeQuery = true)
    List<String> getDialogIdByUsersOwner(String username1, String username2);

    @Query(value = "select u.photo from users u " +
            "where u.username = ? ", nativeQuery = true)
    String getUserPhotoByUsername(String username);

    @Query(value = "select u.email from users u " +
            "where u.username = ? ", nativeQuery = true)
    String getUserEmailByUsername(String username);

    @Query(value = "select u.is_google from users u " +
            "where u.username = ? ", nativeQuery = true)
    String getUserGoogleByUsername(String username);
}