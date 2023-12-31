package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Likes;
import run.itlife.entity.Post;

import java.util.List;

// Уровень доступа к БД
// JpaRepository - специфически переносит методы для работы с реляционными БД.
//В JpaRepository есть все методы CRUD и много других.
//В репозиториях мы объявляем метод, не реализуя его и он по неймингу (если его правильно называем)
//автоматически понимает какой запрос нужно сделать.
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p.*, u.username from post p " +
            "join users u on p.user_id = u.user_id " +
            "where u.username = ? " +
            "GROUP BY p.post_id, u.username;" , nativeQuery = true)
    List<Post> findByUserName(String username);

    @Query(value = "select count(p.post_id) from post p " +
    "join users u on p.user_id = u.user_id " +
    "where u.username = ? ", nativeQuery = true)
    int countPosts(String username);

    @Query(value = "select count(c.post_id), p.post_id from post p " +
            "join comment c on c.post_id = p.post_id where c.post_id = ? " +
            "GROUP BY p.post_id ", nativeQuery = true)
    Long countComments(Long id);

    @Query(value = "select p.*, u.username from post p " +
            "join users u on p.user_id = u.user_id " +
            "where u.username = ? " +
            "order by p.created_at desc ", nativeQuery = true)
    List<Post> sortedPostsByDate(String username);

    @Query(value = "select * from post p " +
            "join subscriptions s on s.user_sub_id = p.user_id " +
            "join users u on u.user_id = s.user_id " +
            "where u.username = ? " +
            "order by p.created_at desc; ", nativeQuery = true)
    List<Post> findSubscribesPosts(String username);

    @Query(value = "select count(p.post_id) from post p " +
            "join subscriptions s on s.user_sub_id = p.user_id " +
            "join users u on u.user_id = s.user_id " +
            "where u.username = ? ", nativeQuery = true)
    int countSubscribesPosts(String username);

    @Query(value = "select p.* from post p " +
            "where p.content LIKE ? ", nativeQuery = true)
    List<Post> searchTags(String substring);

    @Query(value = "select count(p.*) from post p " +
            "where p.content LIKE ? ", nativeQuery = true)
    int countSearchTags(String substring);

    List<Post> findByContentLikeIgnoreCase(String substring);

    @Query(value = "select l.post_id from likes l " +
            "join users u on u.user_id = l.user_id " +
            "where u.username LIKE ? ", nativeQuery = true)
    List<Long> isLikePost(String username);

    @Query(value = "select * from post p " +
            "left join likes l on p.post_id = l.post_id " +
            "left join users u on u.user_id = l.user_id " +
            "where u.username = ? ", nativeQuery = true)
    List<Post> selectMyLikesPosts(String username);

    @Query(value = "select count(p.post_id) from post p " +
            "left join likes l on p.post_id = l.post_id " +
            "left join users u on u.user_id = l.user_id " +
            "where u.username = ? ", nativeQuery = true)
    Long countMyLikesPosts(String username);

}