package run.itlife.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import run.itlife.entity.Post;

public interface PostPaginationRepository extends PagingAndSortingRepository<Post, Long> {
    @Query(value = "select * from post p " +
            "join subscriptions s on s.user_sub_id = p.user_id " +
            "join users u on u.user_id = s.user_id " +
            "where u.username = ? " +
            "order by p.created_at desc ", nativeQuery = true)
    Page<Post> findSubscribesPosts(String username, Pageable pageable);
}