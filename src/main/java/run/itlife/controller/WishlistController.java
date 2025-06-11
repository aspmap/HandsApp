package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.dto.WishlistDto;
import run.itlife.entity.User;
import run.itlife.service.UserService;
import run.itlife.service.WishlistService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

import javax.servlet.ServletContext;

import static run.itlife.messages.ErrorMessages.ERROR;
import static run.itlife.messages.ErrorMessages.NOT_PUBLISH_POST;

@Controller
public class WishlistController {
    @Autowired
    CommonsParams commonsParams;
    private final UserService userService;
    private final WishlistService wishlistService;
    private final ServletContext context;
    private static final Logger log = LoggerFactory.getLogger(WishlistController.class);

    @Autowired
    public WishlistController(UserService userService, WishlistService wishlistService, ServletContext context) {
        this.userService = userService;
        this.wishlistService = wishlistService;
        this.context = context;
    }

    @GetMapping("/wishlist")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String wishlist(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistAll", wishlistService.findAllByUserOrderByCreatedAt(user));
        modelMap.put("countAllWishesByUser", wishlistService.countAllByUser(user.getUserId()));
        modelMap.put("countAllWishesByUserAndIsBookingTrue", wishlistService.countAllByUserAndIsBookingTrue(user.getUserId()));
        return "wishlist/wishlist";
    }

    @GetMapping("/wishlist_my_booking")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String wishlistMyBooking(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("bookingWishlists", wishlistService.findAllByBookingUser(user.getUserId()));
        return "wishlist/view-my-booking";
    }

    @GetMapping("/wishlist_sub/booking/{user_sub}/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String wishlistBooking(ModelMap modelMap, @PathVariable String user_sub, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.bookingWish(user_sub, id);
        return "redirect:" + SaveFile.SEPARATOR + "wishlist_sub" + SaveFile.SEPARATOR + user_sub;
    }

    @GetMapping("/wishlist_sub/unbooking/{user_sub}/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String wishlistUnBooking(ModelMap modelMap, @PathVariable String user_sub, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.unBookingWish(user_sub, id);
        return "redirect:" + SaveFile.SEPARATOR + "wishlist_sub" + SaveFile.SEPARATOR + user_sub;
    }

    @GetMapping("/booking_wishlist/booking/{user_sub}/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String myWishlistBooking(ModelMap modelMap, @PathVariable String user_sub, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.bookingWish(user_sub, id);
        return "redirect:" + SaveFile.SEPARATOR + "wishlist_my_booking";
    }

    @GetMapping("/booking_wishlist/unbooking/{user_sub}/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String myWishlistUnBooking(ModelMap modelMap, @PathVariable String user_sub, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.unBookingWish(user_sub, id);
        return "redirect:" + SaveFile.SEPARATOR + "wishlist_my_booking";
    }

    @GetMapping("/wishlist_sub/{user_sub}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String wishlist_sub(ModelMap modelMap, @PathVariable String user_sub) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("user_sub", user_sub);
        User user = userService.findByUsername(user_sub);
        modelMap.put("wishlistAll", wishlistService.findAllByUserAndSecretIsFalse(user));
        return "wishlist/wishlist-sub";
    }

    @GetMapping("/wishlist/add")
    @PreAuthorize("hasRole('USER')")
    public String newWishlist(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "wishlist/add-to-wishlist";
    }

    @PostMapping("/wishlist/add")
    @PreAuthorize("hasRole('USER')")
    public String addNewTask(WishlistDto wishlistDto, @RequestParam("photo") String file, ModelMap modelMap, @RequestParam(name = "isSecret", defaultValue = "false", required = false) Boolean isSecret) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commonsParams.setCommonParams(modelMap);
        Long wishlistId;
        SaveFile sf = new SaveFile();
        if (!file.isEmpty()) {
            try {
                String filename = sf.saveFileForWishlist(username, context, file);
                if (filename == null) {
                    return "messages-templates" + SaveFile.SEPARATOR + "errorFileSizeWishlist";
                }
                wishlistDto.setPhoto(filename);
                wishlistDto.setSecret(isSecret);
                wishlistId = wishlistService.createElementOfWishlist(wishlistDto);
                return "redirect:" + SaveFile.SEPARATOR + "wishlist";
            } catch (Exception e) {
                log.error(ERROR + e);
                return "messages-templates" + SaveFile.SEPARATOR + "errorFileSizeWishlist";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_POST);
            return "messages-templates" + SaveFile.SEPARATOR + "errorFileSizeWishlist";
        }
    }
}
