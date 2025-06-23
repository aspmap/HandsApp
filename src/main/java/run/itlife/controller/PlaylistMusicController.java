package run.itlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import run.itlife.dto.PlaylistMusicDto;
import run.itlife.entity.Music;
import run.itlife.entity.Playlist;
import run.itlife.service.PlaylistMusicService;
import run.itlife.service.PlaylistService;
import run.itlife.utils.CommonsParams;

import static run.itlife.utils.Properties.Paths.SEPARATOR;

@Controller
public class PlaylistMusicController {
    @Autowired
    CommonsParams commonsParams;
    private final PlaylistMusicService playlistMusicService;
    private final PlaylistService playlistService;

    public PlaylistMusicController(PlaylistMusicService playlistMusicService, PlaylistService playlistService) {
        this.playlistMusicService = playlistMusicService;
        this.playlistService = playlistService;
    }

    @GetMapping("/music/playlist/{playlistId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPlaylist(ModelMap modelMap, @PathVariable Long playlistId) {
        commonsParams.setCommonParams(modelMap);
        modelMap.put("namePlaylist", playlistMusicService.findNamePlaylist(playlistId));
        modelMap.put("playlistId", playlistId);
        modelMap.put("viewPlaylist", playlistMusicService.findPlaylistByPlaylistId(playlistId));
        return "media/view-playlist";
    }

    @GetMapping("/music/addToPlaylist/{musicId}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String addMusicToPlaylist(ModelMap modelMap, @PathVariable Long musicId) {
        commonsParams.setCommonParams(modelMap);
        return "media/add-to-playlist";
    }

    @PostMapping("/music/addToPlaylist")
    @PreAuthorize("hasRole('USER')")
    public String addMusicToPlaylist(ModelMap modelMap, @RequestParam("playlistName") String playlistName, @RequestParam("musicId") Long musicId) {
        Long playlistId = playlistService.findPlaylistId(playlistName);
        Playlist playlist = new Playlist();
        playlist.setPlaylistId(playlistId);
        Music music = new Music();
        music.setMusicId(musicId);
        PlaylistMusicDto playlistMusicDto = new PlaylistMusicDto();
        playlistMusicDto.setMusicId(music);
        playlistMusicDto.setPlaylistId(playlist);
        playlistMusicService.addToPlaylist(playlistMusicDto);
        return "redirect:" + SEPARATOR + "music/playlist/" + playlistId;
    }

    @PostMapping("/music/playlist/song/delete/{playlistId}/{musicId}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMusicFromPlaylist(@PathVariable Long playlistId, @PathVariable Long musicId) {
        playlistMusicService.deleteMusicFromPlaylist(playlistId, musicId);
    }
}
