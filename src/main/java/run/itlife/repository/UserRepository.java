package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Уровень доступа к БД
// JpaRepository - специфически переносит методы для работы с реляционными БД.
//В JpaRepository есть все методы CRUD и много других.
//В репозиториях мы объявляем метод, не реализуя его и он по неймингу (если его правильно называем)
//автоматически понимает какой запрос нужно сделать.
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // Возвращает юзера

    User findByUsername(User user); // Возвращает юзера

    @Query(value = "select * from users u " +
            "join user_role ur on ur.user_id = u.user_id " +
            "join role r on r.role_id = ur.role_id " +
            "where r.name = 'USER' " +
            "order by u.created_at desc " +
            "LIMIT 5 ", nativeQuery = true)
    List<User> getUsersOnly();

  /*  @Query(value = "select u.user_id, u.username, u.photo, u.surname, u.firstname, u.created_at, u.email, u.info, u.is_active, u.www, u.phone, u.sex, u.password, " +
            "(select count(s.sub_id) from subscriptions s " +
            "join users u2 on u2.user_id = s.user_sub_id " +
            "join users u1 on u1.user_id = s.user_id " +
            "where u1.username = ? and u2.username = u.username) isSub " +
            "from users u " +
            "join user_role ur on ur.user_id = u.user_id " +
            "join role r on r.role_id = ur.role_id " +
            "where r.name = 'USER' " +
            "order by u.created_at desc " +
            "LIMIT 5 ; ", nativeQuery = true)
    List<User> getUsersOnly(String currentUsername);*/

    @Query(value = "select " +
            "(select count(s.sub_id) from subscriptions s " +
            "join users u2 on u2.user_id = s.user_sub_id " +
            "join users u1 on u1.user_id = s.user_id " +
            "where u1.username = ? and u2.username = u.username) isSub, CONCAT(u.username, ' ') as username, CONCAT(u.photo, ' ') as photo, CONCAT(u.firstname, ' ') as firstname, CONCAT(u.surname, ' ') as surname, CONCAT(u.email, ' ') as email, CONCAT(u.is_google, ' ') as isGoogle " +
            "from users u " +
            "join user_role ur on ur.user_id = u.user_id " +
            "join role r on r.role_id = ur.role_id " +
            "where r.name = 'USER' " +
            "order by u.created_at desc " +
            "LIMIT 5 ; ", nativeQuery = true)
    ArrayList<String> getUsersOnlyKey(String currentUsername);


    @Query(value = "select u.* from users u " +
            "left join user_role ur on u.user_id = ur.user_id " +
            "left join role r on ur.role_id = r.role_id " +
    "where u.username LIKE ? AND r.name LIKE 'USER'", nativeQuery = true)
    List<User> searchUsers(String substring);

    @Query(value = "select count(u.username) from users u " +
            "where u.username LIKE ? ", nativeQuery = true)
    int countSearchUsers(String substring);

}