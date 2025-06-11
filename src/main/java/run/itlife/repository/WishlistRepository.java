package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;

import java.util.ArrayList;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    ArrayList<Wishlist> findAllByUserOrderByCreatedAt(User user);
    ArrayList<Wishlist> findAllByBookingUser(Long bookingUserId);

    @Query(value = "select * from wishlist w " +
            "where w.is_secret = 'false' and w.user_id = ? " +
            "order by w.created_at ", nativeQuery = true)
    ArrayList<Wishlist> findAllByUserAndSecretIsFalse(User user);
}
