package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import run.itlife.dto.MusicDto;
import run.itlife.entity.User;
import run.itlife.service.MusicService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

import javax.servlet.ServletContext;
import java.util.Map;

import static run.itlife.messages.ErrorMessages.ERROR;
import static run.itlife.messages.ErrorMessages.NOT_PUBLISH_POST;

@Controller
public class MusicController {
    private final MusicService musicService;
    private final UserService userService;
    private final ServletContext context;
    @Autowired
    CommonsParams commonsParams;
    private static final int MAX_UPLOAD_MUSIC_FILE_SIZE_IN_MB = 100 * 1024 * 1024; // 100 МБ
    private static final Logger log = LoggerFactory.getLogger(MusicController.class);

    @Autowired
    public MusicController(MusicService musicService, UserService userService, ServletContext context) {
        this.musicService = musicService;
        this.userService = userService;
        this.context = context;
    }

    @GetMapping("/music")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String posts(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("musicAll", musicService.findAllByUser(user));
        return "media/music";
    }

    @GetMapping("/music/add")
    @PreAuthorize("hasRole('USER')")
    public String addMusic(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "media/add-music";
    }

    @PostMapping("/music/add")
    @PreAuthorize("hasRole('USER')")
    public String addNewMusic(MusicDto musicDto, @RequestParam("file") MultipartFile file, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commonsParams.setCommonParams(modelMap);
        if (file.getSize() > MAX_UPLOAD_MUSIC_FILE_SIZE_IN_MB) {
            return "messages-templates" + SaveFile.SEPARATOR + "errorFileSizeMusic";
        }
        Long musicId;
        SaveFile sf = new SaveFile();

        if (!file.isEmpty()) {
            try {
                Map<String, String> filenameMap = sf.saveMusicFile(username, context, file);
                if (filenameMap == null) {
                    return "messages-templates" + SaveFile.SEPARATOR + "errorFileSizeMusic";
                }
                for (Map.Entry<String, String> entry : filenameMap.entrySet()) {
                    musicDto.setFileName(entry.getKey());
                }
                musicId = musicService.createSong(musicDto);
                return "redirect:" + SaveFile.SEPARATOR + "music";
            } catch (Exception e) {
                log.error(ERROR + e);
                return "messages-templates" + SaveFile.SEPARATOR + "errorFileSizeMusic";
            }
        } else {
            log.error(ERROR + NOT_PUBLISH_POST);
            return "messages-templates" + SaveFile.SEPARATOR + "errorFileSizeMusic";
        }
    }
}