package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Subscriptions;

import java.util.ArrayList;
import java.util.List;

public interface HandshakeRepository extends JpaRepository<Subscriptions, Long> {
    @Query(value = "select s1.user_sub_id from subscriptions s1 " +
            " where s1.user_id = ? ", nativeQuery = true)
    ArrayList<Integer> findUsersId(Integer userId);

    @Query(value = "select u.username, u.photo from users u " +
            " where u.user_id = ? ", nativeQuery = true)
    List<String> findUsersById(Integer userId);




}
