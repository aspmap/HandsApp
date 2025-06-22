package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import run.itlife.dto.PlaylistDto;
import run.itlife.entity.User;
import run.itlife.service.PlaylistService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

@Controller
public class PlaylistController {
    @Autowired
    CommonsParams commonsParams;
    private final UserService userService;
    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(UserService userService, PlaylistService playlistService) {
        this.userService = userService;
        this.playlistService = playlistService;
    }

    @GetMapping("/music/playlists")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String playlists(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("playlistAll", playlistService.getAllPlaylistsByUserId(user.getUserId()));
        return "media/playlists";
    }

    @GetMapping("/music/playlist/create")
    @PreAuthorize("hasRole('USER')")
    public String createPlaylist(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "media/create-playlist";
    }

    @PostMapping("/music/playlist/create")
    @PreAuthorize("hasRole('USER')")
    public String confirmMusic(PlaylistDto playlistDto, ModelMap modelMap) {
        playlistService.createPlaylist(playlistDto);
        return "redirect:" + SaveFile.SEPARATOR + "music/playlists";
    }

    @PostMapping("/music/playlist/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
    }
}
