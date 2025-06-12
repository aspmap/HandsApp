package run.itlife.service;

import run.itlife.dto.WishlistDto;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;

import java.util.ArrayList;

public interface WishlistService {
    ArrayList<Wishlist> findAllByUserOrderByCreatedAt(Long userId);
    ArrayList<Wishlist> findAllByBookingUser(Long bookingUserId);
    ArrayList<Wishlist> findAllByUserAndIsDoneTrue(User user);
    Long createElementOfWishlist(WishlistDto wishlistDto);
    ArrayList<Wishlist> findAllByUserAndSecretIsFalse(User user);
    void bookingWish(String user_sub, Long id);
    void unBookingWish(String user_sub, Long id);
    Long countAllByUserAndIsBookingTrue(Long userId);
    Long countAllByUser(Long userId);
    void deleteWish(Long id);
    void checkCompleteWish(Long wishId);
}