package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import run.itlife.dto.PlaylistDto;
import run.itlife.entity.User;
import run.itlife.service.PlaylistService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;

import static run.itlife.utils.Properties.ErrorMessages.*;
import static run.itlife.utils.Properties.Paths.*;

@Controller
public class PlaylistController {
    @Autowired
    CommonsParams commonsParams;
    private final UserService userService;
    private final PlaylistService playlistService;
    private static final Logger log = LoggerFactory.getLogger(PlaylistController.class);

    @Autowired
    public PlaylistController(UserService userService, PlaylistService playlistService) {
        this.userService = userService;
        this.playlistService = playlistService;
    }

    @GetMapping("/music/playlists")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPlaylists(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("playlistAll", playlistService.findAllPlaylistsByUserId(user.getUserId()));
        return "media/playlists";
    }

    @GetMapping("/music/playlist/create")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createPlaylist(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "media/create-playlist";
    }

    @PostMapping("/music/playlist/create")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createPlaylist(PlaylistDto playlistDto, ModelMap modelMap) {
        Long playlistId = playlistService.createPlaylist(playlistDto);
        if (playlistId == null) {
            log.error(ERROR + NOT_PUBLISH_PLAYLIST);
            commonsParams.setCommonParams(modelMap);
            return "messages-templates" + SEPARATOR + "error";
        }
        return "redirect:" + SEPARATOR + "music/playlists";
    }

    @DeleteMapping("/music/playlist/delete/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
    }
}
