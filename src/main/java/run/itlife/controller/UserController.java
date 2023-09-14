package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import run.itlife.enums.Sex;
import run.itlife.service.PostService;
import run.itlife.service.SubscriptionsService;
import run.itlife.service.UserService;
import javax.imageio.ImageIO;
import javax.persistence.EntityExistsException;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import static run.itlife.utils.EditImage.resizeImage;
import static run.itlife.utils.OtherUtils.generateFileName;

//UserController, отвечающий за логин юзеров и т.д.
//Создаем в папке view страницу register.html. Далее необходимо сделать, чтобы мы пересылали данные в контроллер.
//У UserController будет страница по которой будет идти регистрация. Для этого нужно сделать форму и она уже будет
//идти на контроллер для регистрации
@Controller
public class UserController {
    private static String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    private final UserService userService;
    private final SubscriptionsService subscriptionsService;
    private final PostService postService;
    private final ServletContext context;

    @Autowired
    public UserController(UserService userService, ServletContext context, SubscriptionsService subscriptionsService, PostService postService) {
        this.userService = userService;
        this.context = context;
        this.subscriptionsService = subscriptionsService;
        this.postService = postService;
    }

    @GetMapping("/login")
    public String login(ModelMap model){
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "login";
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap){
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        try {
            userService.create(user);
            return "messages-templates/registration-success";
        } catch (EntityExistsException e) {
            return "messages-templates/exist";
        }
    }

    @GetMapping("/profile_delete/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profile_delete(ModelMap modelMap, @PathVariable String user){
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(user)) {
            setCommonParams(modelMap);
            return "messages-templates/404";
        }
        setCommonParams(modelMap, user);
        return "profile/profile-delete";
    }

    @PostMapping("/profile_delete/{user}")
    @PreAuthorize("hasRole('USER')")
    public String delete_profile(ModelMap modelMap, @PathVariable String user){
        userService.delete_profile(user);
        //удаляем папки и файлы пользователя
        File dir_img = new File(context.getRealPath("/resources/img/users/" + user));
        File dir_video = new File(context.getRealPath("/resources/video/users/" + user));
        recursiveDelete(dir_img);
        recursiveDelete(dir_video);
        return "redirect:/";
    }

    @GetMapping("/profile_edit/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profile_edit(ModelMap modelMap, @PathVariable String user){
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(user)) {
            setCommonParams(modelMap);
            return "messages-templates/404";
        }
        setCommonParams(modelMap, user);
        modelMap.put("sex_male", Sex.MALE);
        modelMap.put("sex_female", Sex.FEMALE);
        return "profile/profile-edit";
    }

    @PostMapping("/profile_edit")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profile_edit(UserDto userDto, @RequestParam("file") String file, ModelMap modelMap) {
        setCommonParams(modelMap);

       if (!file.isEmpty()) {
            try {
                // изменение и генерация ноового имени файла
                String filename = generateFileName() + ".jpg";
                // получение имени фото и сохранение имени фото и данных поста в БД
                userService.checkAuthority(userDto.getUserId());
                userDto.setPhoto(filename);
                userService.update(userDto);

                // сохранение самого файла в папку юзера
                final String username = SecurityContextHolder.getContext().getAuthentication().getName();
                File dir = new File(context.getRealPath("/resources/img/users/" + username + "/profile/"));
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File uploadedFile = new File(dir + "/" + filename);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));

                String base64Image = file.split(",")[1];
                byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
                stream.write(imageBytes);

                //изменение размера до 500х500
                BufferedImage originalImage = ImageIO.read(uploadedFile);
                BufferedImage resizeImage = null;
                File newFileJPG = null;
                resizeImage = resizeImage(originalImage, 500, 500);
                newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);

                //записываем файл
                ImageIO.write(resizeImage, "png", newFileJPG);
                stream.flush();
                stream.close();
                return "redirect:/posts/";
            } catch (Exception e) {
                return "messages-templates/error";
            }
        } else {
           userService.checkAuthority(userDto.getUserId());
           userService.update(userDto);
           return "redirect:/";
        }
    }

    @GetMapping("/subscriptions")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribes(ModelMap modelMap) {
        setCommonParams(modelMap);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("sub", subscriptionsService.findSubscribes(username));
        return "subs/subscriptions";
    }

    @GetMapping("/subscribers")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribers(ModelMap modelMap) {
        setCommonParams(modelMap);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("sub", subscriptionsService.findSubscribers(username));
        return "subs/subscribers";
    }

    @GetMapping("/subscriptions_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribes_subuser(ModelMap modelMap, @PathVariable String user) {
        setCommonParams(modelMap);
        modelMap.put("sub", subscriptionsService.findSubscribes(user));
        return "subs/subscriptions-subuser";
    }

    @GetMapping("/subscribers_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribers_subuser(ModelMap modelMap, @PathVariable String user) {
        setCommonParams(modelMap);
        modelMap.put("sub", subscriptionsService.findSubscribers(user));
        return "subs/subscribers-subuser";
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String search(ModelMap modelMap, @RequestParam(required = false) String search) {
        search = search.toLowerCase();
        setCommonParams(modelMap);
        modelMap.put("countSearchUsers", userService.countSearchUsers(search));
        modelMap.put("countSearchTags", postService.countSearchTags(search));
        modelMap.put("tagUserName", search);
        if (search != null) {
            modelMap.put("findUsers", userService.searchUsers(search));
            modelMap.put("findTags", postService.searchTags(search));
            return "search-results";
        } else {
            modelMap.put("findUsers", userService.findAll());
            return "search-results";
        }
    }

    private void setCommonParams(ModelMap modelMap, String user) {
        modelMap.put("user", user);
        modelMap.put("userinfo", userService.findByUsername(user));
    }

    private void setCommonParams(ModelMap modelMap) {
        //setCommonParams(modelMap); //TODO зачем это?
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userslist", userService.findAll());
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
    }

    public static void recursiveDelete(File file) {
        if (!file.exists())
            return;

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                recursiveDelete(f);
            }
        }
        file.delete();
    }

}