package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;

import java.util.ArrayList;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query(value = "select * from wishlist w " +
            "where (w.is_done = 'false' or w.is_done is null) and w.user_id = ? " +
            "order by w.created_at ", nativeQuery = true)
    ArrayList<Wishlist> findAllByUserOrderByCreatedAt(Long userId);

    ArrayList<Wishlist> findAllByBookingUser(Long bookingUserId);

    ArrayList<Wishlist> findAllByUserAndIsDoneTrue(User user);

    @Query(value = "select count(w.wishlist_id) from wishlist w where w.is_booking = 'true' and w.user_id = ? and (w.is_done = 'false' or w.is_done is null) ", nativeQuery = true)
    Long countAllByUserAndIsBookingTrue(Long userId);

    @Query(value = "select count(w.wishlist_id) from wishlist w where w.user_id = ? and (w.is_done = 'false' or w.is_done is null) ", nativeQuery = true)
    Long countAllByUser(Long userId);

    @Query(value = "select * from wishlist w " +
            "where w.is_secret = 'false' and w.user_id = ? " +
            "order by w.created_at ", nativeQuery = true)
    ArrayList<Wishlist> findAllByUserAndSecretIsFalse(User user);
}
