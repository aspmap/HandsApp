package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.WishlistPrivate;

public interface WishlistPrivateRepository extends JpaRepository<WishlistPrivate, Long> {
    @Query(value = "select count(*) from wishlist_private wp " +
            "where wp.wishlist_id = ? and wp.user_id = ? ", nativeQuery = true)
    Integer searchAlreadyPermissions(Long id, Long userId);
}
