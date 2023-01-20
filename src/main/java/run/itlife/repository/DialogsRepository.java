package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Dialogs;
import java.util.List;

public interface DialogsRepository extends JpaRepository<Dialogs, Long> {

    @Query(value = "select * from dialogs d " +
            "left join user_dialog ud on d.dialog_id = ud.dialog_id " +
            "left join users u on u.user_id = ud.user_id " +
            "where u.username = ? " +
            "order by d.created_at desc ", nativeQuery = true)
    List<Dialogs> findDialogsByUsername(String username);

    @Query(value = "select * from dialogs d " +
            "order by d.created_at desc ", nativeQuery = true)
    List<Dialogs> sortedDialogsByDate();

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

}