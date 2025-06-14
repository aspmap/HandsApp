package run.itlife.controller;

import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import run.itlife.dto.WishlistDto;
import run.itlife.dto.WishlistPrivateDto;
import run.itlife.entity.User;
import run.itlife.entity.Wishlist;
import run.itlife.service.UserService;
import run.itlife.service.WishlistPrivateService;
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
    private final WishlistPrivateService wishlistPrivateService;
    private final ServletContext context;
    private static final Logger log = LoggerFactory.getLogger(WishlistController.class);

    @Autowired
    public WishlistController(UserService userService, WishlistService wishlistService, WishlistPrivateService wishlistPrivateService, ServletContext context) {
        this.userService = userService;
        this.wishlistService = wishlistService;
        this.wishlistPrivateService = wishlistPrivateService;
        this.context = context;
    }

    @GetMapping("/wishlist")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String wishlist(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistAll", wishlistService.findAllByUserOrderByCreatedAt(user.getUserId()));
        modelMap.put("countAllWishesByUser", wishlistService.countAllByUser(user.getUserId()));
        modelMap.put("countAllWishesByUserAndIsBookingTrue", wishlistService.countAllByUserAndIsBookingTrue(user.getUserId()));
        modelMap.put("whoSeesSecretWishes", wishlistService.whoSeesSecretWishes(user.getUserId()));
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userCurrent = userService.findByUsername(username);
        modelMap.put("wishlistPrivate", wishlistService.findPrivateWishesByUser(userCurrent.getUserId(), user.getUserId()));
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

    @PostMapping("/wishlist/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWish(@PathVariable Long id) {
        wishlistService.deleteWish(id);
    }

    @GetMapping("/wishlist/done")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String wishlistDone(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistIsdone", wishlistService.findAllByUserAndIsDoneTrue(user));
        return "wishlist/wishlist-done";
    }

    @GetMapping("/wishlist/complete/{wishId}")
    @PreAuthorize("hasRole('USER')")
    public String completeWishlist(ModelMap modelMap, @PathVariable Long wishId) {
        commonsParams.setCommonParams(modelMap);
        wishlistService.checkCompleteWish(wishId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistAll", wishlistService.findAllByUserOrderByCreatedAt(user.getUserId()));
        modelMap.put("countAllWishesByUser", wishlistService.countAllByUser(user.getUserId()));
        modelMap.put("countAllWishesByUserAndIsBookingTrue", wishlistService.countAllByUserAndIsBookingTrue(user.getUserId()));
        return "wishlist/wishlist";
    }

    @GetMapping("/wishlist/permissions/add/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String addPermissions(ModelMap modelMap, @PathVariable Long id) {
        commonsParams.setCommonParams(modelMap);
        return "wishlist/add-permission";
    }

    @PostMapping("/wishlist/permissions/add")
    @PreAuthorize("hasRole('USER')")
    public String addPermissionsPost(ModelMap modelMap, @RequestParam("userWishlistPrivate") String username, @RequestParam("wishlistIdWishlistPrivate") Long id) {
        WishlistPrivateDto  wishlistPrivateDto = new WishlistPrivateDto();
        Integer countAlreadyPermission;
        User user = new User();
        commonsParams.setCommonParams(modelMap);
        try {
            user = userService.findByUsername(username);
            countAlreadyPermission = wishlistPrivateService.searchAlreadyPermissions(id, user.getUserId());
        } catch (UsernameNotFoundException e) {
            log.error(ERROR + e);
            return "messages-templates" + SaveFile.SEPARATOR + "existPermission";
        }

        if (countAlreadyPermission == 0) {
            Wishlist wishlist = new Wishlist();
            wishlist.setWishlistId(id);
            wishlistPrivateDto.setWishlistIdWishlistPrivate(wishlist);
            wishlistPrivateDto.setUserWishlistPrivate(user);
            wishlistService.addPermission(wishlistPrivateDto);
            return "redirect:" + SaveFile.SEPARATOR + "wishlist";
        }
        return "messages-templates" + SaveFile.SEPARATOR + "existPermission";

    }

    @GetMapping("/wishlist/permissions/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public String deletePermissions(ModelMap modelMap, @PathVariable Long id) {
        wishlistPrivateService.deletePermissions(id);
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("wishlistAll", wishlistService.findAllByUserOrderByCreatedAt(user.getUserId()));
        modelMap.put("countAllWishesByUser", wishlistService.countAllByUser(user.getUserId()));
        modelMap.put("countAllWishesByUserAndIsBookingTrue", wishlistService.countAllByUserAndIsBookingTrue(user.getUserId()));
        modelMap.put("whoSeesSecretWishes", wishlistService.whoSeesSecretWishes(user.getUserId()));
        return "wishlist/wishlist";
    }
}
