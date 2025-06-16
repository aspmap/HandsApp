package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import run.itlife.dto.PlaylistDto;
import run.itlife.dto.PlaylistMusicDto;
import run.itlife.entity.Music;
import run.itlife.entity.Playlist;
import run.itlife.service.PlaylistMusicService;
import run.itlife.service.PlaylistService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

@Controller
public class PlaylistMusicController {
    @Autowired
    CommonsParams commonsParams;
    private final UserService userService;
    private final PlaylistMusicService playlistMusicService;
    private final PlaylistService playlistService;

    public PlaylistMusicController(UserService userService, PlaylistMusicService playlistMusicService, PlaylistService playlistService) {
        this.userService = userService;
        this.playlistMusicService = playlistMusicService;
        this.playlistService = playlistService;
    }

    @GetMapping("/music/playlist/{playlistId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String playlist(ModelMap modelMap, @PathVariable Long playlistId) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("namePlaylist", playlistMusicService.getNamePlaylist(playlistId));
        modelMap.put("playlistId", playlistId);
        modelMap.put("viewPlaylist", playlistMusicService.getPlaylistByPlaylistId(playlistId));
        return "media/view-playlist";
    }

    @GetMapping("/music/addToPlaylist/{musicId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String addToPlaylist(ModelMap modelMap, @PathVariable Long musicId) {
        commonsParams.setCommonParams(modelMap);
        return "media/add-to-playlist";
    }

    @PostMapping("/music/addToPlaylist")
    @PreAuthorize("hasRole('USER')")
    public String addToPlaylistPost(ModelMap modelMap, @RequestParam("playlistName") String playlistName, @RequestParam("musicId") Long musicId) {
        commonsParams.setCommonParams(modelMap);
        Long playlistId = playlistService.getPlaylistId(playlistName);
        Playlist playlist = new Playlist();
        playlist.setPlaylistId(playlistId);
        Music music = new Music();
        music.setMusicId(musicId);
        PlaylistMusicDto playlistMusicDto = new PlaylistMusicDto();
        playlistMusicDto.setMusicId(music);
        playlistMusicDto.setPlaylistId(playlist);
        playlistMusicService.addToPlaylist(playlistMusicDto);
        return "redirect:" + SaveFile.SEPARATOR + "music/playlist/" + playlistId;
    }

    @PostMapping("/music/playlist/song/delete/{playlistId}/{musicId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFromPlaylist(@PathVariable Long playlistId, @PathVariable Long musicId) {
        playlistMusicService.deleteFromPlaylist(playlistId, musicId);
    }
}
