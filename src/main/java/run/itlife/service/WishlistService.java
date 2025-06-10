package run.itlife.service;

import run.itlife.dto.WishlistDto;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;

import java.util.ArrayList;

public interface WishlistService {
    ArrayList<Wishlist> findAllByUserOrderByCreatedAt(User user);
    Long createElementOfWishlist(WishlistDto wishlistDto);
    ArrayList<Wishlist> findAllByUserAndSecretIsFalse(User user);
}