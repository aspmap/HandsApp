package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Dialogs;
import run.itlife.entity.Subscriptions;

import java.util.List;
import java.util.Map;

public interface HandshakeRepository extends JpaRepository<Subscriptions, Long> {

    @Query(value = "select s1.user_id from subscriptions s1 " +
            "order by s1.sub_id ", nativeQuery = true)
    List<Long> selectUsersId();

    @Query(value = "select s2.user_sub_id from subscriptions s2 " +
            "order by s2.sub_id ", nativeQuery = true)
    List<Long> selectUsersSubId();

    @Query(value = "select u.username, u.photo from users u " +
            " where u.user_id = ? ", nativeQuery = true)
    List<String> findUsersById(Long userId);




}
