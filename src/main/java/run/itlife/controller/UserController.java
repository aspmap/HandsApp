package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;
import run.itlife.enums.Sex;
import run.itlife.service.PostService;
import run.itlife.service.SubscriptionsService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.Profile;
import run.itlife.utils.info.InformationGathering;
import run.itlife.utils.info.InformationGatheringArchive;
import run.itlife.utils.info.InformationGatheringInfo;
import run.itlife.utils.info.InformationGatheringMedia;

import javax.imageio.ImageIO;
import javax.persistence.EntityExistsException;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

import static run.itlife.messages.ErrorMessages.ERROR;
import static run.itlife.utils.EditImage.resizeImage;
import static run.itlife.utils.OtherUtils.generateFileName;
import static run.itlife.utils.SaveFile.SEPARATOR;

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
    private final InformationGatheringMedia informationGatheringMedia;
    private final InformationGatheringInfo informationGatheringInfo;
    private final InformationGatheringArchive informationGatheringArchive;
    private static final String PATH_VIDEO_USERS = "/resources/video/users/";
    private static final String PATH_IMAGE_USERS = "/resources/img/users/";
    private static final String PATH_FILES = "/resources/users_archive/users/";
    @Autowired
    CommonsParams commonsParams;
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, ServletContext context, SubscriptionsService subscriptionsService, PostService postService, InformationGatheringMedia informationGatheringMedia, InformationGatheringInfo informationGatheringInfo, InformationGatheringArchive informationGatheringArchive) {
        this.userService = userService;
        this.context = context;
        this.subscriptionsService = subscriptionsService;
        this.postService = postService;
        this.informationGatheringMedia = informationGatheringMedia;
        this.informationGatheringInfo = informationGatheringInfo;
        this.informationGatheringArchive = informationGatheringArchive;
    }

    @GetMapping("/login")
    public String login(ModelMap model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(), authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        commonsParams.setCommonConstParams(model);
        return "login";
    }

    @GetMapping("/error")
    public String loginError(ModelMap modelMap) {
        return "messages-templates/loginError";
    }

    @GetMapping("/register")
    public String register(ModelMap modelMap) {
        commonsParams.setCommonConstParams(modelMap);
        return "register";
    }

    @GetMapping("/confidentiality")
    public String confidentiality(ModelMap modelMap) {
        return "confidentiality";
    }

    @PostMapping("/register")
    public String register(User user) {
        try {
            userService.create(user);
            return "messages-templates/registration-success";
        } catch (EntityExistsException e) {
            log.error(ERROR + e);
            return "messages-templates/exist";
        }
    }

    @GetMapping("/profile_delete/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profile_delete(ModelMap modelMap, @PathVariable String user) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(user)) {
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/404";
        }
        commonsParams.setCommonParams(modelMap, user);
        return "profile/profile-delete";
    }

    @PostMapping("/profile_delete/{user}")
    @PreAuthorize("hasRole('USER')")
    public String delete_profile(ModelMap modelMap, @PathVariable String user) {
        userService.delete_profile(user);
        //удаляем папки и файлы пользователя
        File dir_img = new File(context.getRealPath("/resources/img/users/" + user));
        File dir_video = new File(context.getRealPath("/resources/video/users/" + user));
        Profile.recursiveFilesDelete(dir_img);
        Profile.recursiveFilesDelete(dir_video);
        return "redirect:/";
    }

    @GetMapping("/profile_edit/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profile_edit(ModelMap modelMap, @PathVariable String user) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(user)) {
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/404";
        }
        commonsParams.setCommonParams(modelMap, user);
        modelMap.put("sex_male", Sex.MALE);
        modelMap.put("sex_female", Sex.FEMALE);
        return "profile/profile-edit";
    }

    @PostMapping("/profile_edit")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profile_edit(UserDto userDto, @RequestParam("file") String file, ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);

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
                log.error(ERROR + e);
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
        commonsParams.setCommonParams(modelMap);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("sub", subscriptionsService.findSubscribes(username));
        return "subs/subscriptions";
    }

    @GetMapping("/subscribers")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribers(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("sub", subscriptionsService.findSubscribers(username));
        return "subs/subscribers";
    }

    @GetMapping("/subscriptions_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribes_subuser(ModelMap modelMap, @PathVariable String user) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("sub", subscriptionsService.findSubscribes(user));
        return "subs/subscriptions-subuser";
    }

    @GetMapping("/subscribers_subuser/{user}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String find_Subscribers_subuser(ModelMap modelMap, @PathVariable String user) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("sub", subscriptionsService.findSubscribers(user));
        return "subs/subscribers-subuser";
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String search(ModelMap modelMap, @RequestParam(required = false) String search) {
        search = search.toLowerCase();
        commonsParams.setCommonParams(modelMap);
        modelMap.put("countSearchUsers", userService.countSearchUsers(search));
        modelMap.put("countSearchGoogleUsers", userService.countSearchGoogleUsers(search));
        modelMap.put("countSearchTags", postService.countSearchTags(search));
        modelMap.put("tagUserName", search);
        if (search != null) {
            modelMap.put("findUsers", userService.searchUsers(search));
            modelMap.put("findGoogleUsers", userService.searchGoogleUsers(search));
            modelMap.put("findTags", postService.searchTags(search));
            return "search-results";
        } else {
            modelMap.put("findUsers", userService.findAll());
            return "search-results";
        }
    }

    @GetMapping("/get_profile_archive")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String getProfileArchive(ModelMap modelMap) throws ExecutionException, InterruptedException {
        // Используем Executor и Future
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Future<String> page = executorService.submit(
                new Callable<>() {
                    boolean isAvailableArchive = false;
                    final String username = SecurityContextHolder.getContext().getAuthentication().getName();
                    @Override
                    public String call() {
                        File dirOriginPhoto = new File(context.getRealPath(PATH_IMAGE_USERS + username));
                        File dirOriginVideo = new File(context.getRealPath(PATH_VIDEO_USERS + username));
                        File dirOriginProfilePhoto = new File(context.getRealPath(PATH_IMAGE_USERS + username + SEPARATOR + "profile"));
                        File dirDestinationPhoto = new File(context.getRealPath(PATH_FILES + username) + SEPARATOR + "img");
                        File dirDestinationVideo = new File(context.getRealPath(PATH_FILES + username) + SEPARATOR + "video");
                        File dirDestinationProfilePhoto = new File(context.getRealPath(PATH_FILES + username) + SEPARATOR + "img/profile");
                        File dirDestinationInfo = new File(context.getRealPath(PATH_FILES + username));
                        File dirOriginArchive = new File(context.getRealPath(PATH_FILES + username));

                        informationGatheringMedia.copyUserInfo(username, dirOriginPhoto, dirDestinationPhoto);
                        informationGatheringMedia.copyUserInfo(username, dirOriginProfilePhoto, dirDestinationProfilePhoto);
                        informationGatheringMedia.copyUserInfo(username, dirOriginVideo, dirDestinationVideo);
                        informationGatheringInfo.copyUserInfo(username, null, dirDestinationInfo);
                        informationGatheringArchive.copyUserInfo(username, dirOriginArchive, null);
                        File dirOriginArchiveZip = new File(dirOriginArchive + ".zip");
                        if (dirOriginArchiveZip.exists()) {
                            isAvailableArchive = true;
                        }
                        setCommonParamsSynchronized(modelMap, username);
                        modelMap.put("isAvailableArchive", isAvailableArchive);
                        return "messages-templates/download-archive";
                    }
                });
        executorService.shutdown();
        return profileArchiveGenerate(modelMap);
    }

    @GetMapping("/profile_archive")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profileArchive(ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAvailableArchive = false;
        File dirOriginArchiveZip = new File(context.getRealPath(PATH_FILES + username) + ".zip");
        if (dirOriginArchiveZip.exists()) {
            isAvailableArchive = true;
        }
        commonsParams.setCommonParams(modelMap);
        modelMap.put("isAvailableArchive", isAvailableArchive);
        return "messages-templates/download-archive";
    }

    @GetMapping("/profile_archive_generate")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String profileArchiveGenerate(ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAvailableArchive = false;
        File dirOriginArchiveZip = new File(context.getRealPath(PATH_FILES + username) + ".zip");
        if (dirOriginArchiveZip.exists()) {
            isAvailableArchive = true;
        }
        commonsParams.setCommonParams(modelMap);
        modelMap.put("isAvailableArchive", isAvailableArchive);
        return "messages-templates/formation-archive";
    }

    private void setCommonParamsSynchronized(ModelMap modelMap, String username) {
        modelMap.put("users", userService.findAll());
        modelMap.put("userslist", userService.findAll());
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
    }
}