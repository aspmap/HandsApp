package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Dialogs;

import java.util.ArrayList;
import java.util.Map;

public interface DialogsRepository extends JpaRepository<Dialogs, Long> {
    @Query(value = "select count(*) from users u " +
            "left join user_dialog ud on u.user_id = ud.user_id " +
            "left join dialogs d on ud.dialog_id = d.dialog_id " +
            "left join dialogs d2 on d2.dialog_id = d.dialog_id " +
            "left join user_dialog ud2 on ud2.dialog_id = d2.dialog_id " +
            "left join users u2 on u2.user_id = ud2.user_id " +
            "where u.username = ? and u2.username = ? ", nativeQuery = true)
    byte checkDuplicateDialogues(String username1, String username2);

    @Query(value = "select d.dialog_id from users u " +
            "left join user_dialog ud on u.user_id = ud.user_id " +
            "left join dialogs d on ud.dialog_id = d.dialog_id " +
            "left join dialogs d2 on d2.dialog_id = d.dialog_id " +
            "left join user_dialog ud2 on ud2.dialog_id = d2.dialog_id " +
            "left join users u2 on u2.user_id = ud2.user_id " +
            "where u.username = ? and u2.username = ? ", nativeQuery = true)
    Long getDialogIdByUsers(String username1, String username2);

    @Query(value = "select d.dialog_id from dialogs d " +
            "left join user_dialog ud on d.dialog_id = ud.dialog_id " +
            "left join users u on u.user_id = ud.user_id " +
            "where u.username = ? " +
            "order by d.updated_at desc ", nativeQuery = true)
    ArrayList<Long> findDialogsIdByUsername(String username);

    @Query(value = "select * from dialogs d " +
            "left join user_dialog ud on d.dialog_id = ud.dialog_id " +
            "left join users u on u.user_id = ud.user_id " +
            "where u.username = ? " +
            "order by d.updated_at desc ", nativeQuery = true)
    ArrayList<Dialogs> findDialogsByUsername(String username);

    @Query(value = "select count(m.dialog_id) from dialogs d " +
            "left join messages m on d.dialog_id = m.dialog_id " +
            "where d.dialog_id = ? ", nativeQuery = true)
    Integer showCountDialog(Long dialogId);

    @Query(value = "select cast(d.dialog_id as INTEGER) as dialog_id, cast(count(*) as INTEGER) as count_messages from messages m " +
            "left join dialogs d on d.dialog_id = m.dialog_id " +
            "left join users u on u.user_id = m.user_id " +
            "where d.dialog_id = ? and m.is_read = false and u.username = ? " +
            "group by d.dialog_id ", nativeQuery = true)
    Map<String, Integer> countUnreadMessagesInDialog(Long dialogId, String username);
}