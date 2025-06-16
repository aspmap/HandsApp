package run.itlife.service;

import run.itlife.dto.PlaylistDto;
import run.itlife.entity.Playlist;

import java.util.ArrayList;

public interface PlaylistService {
    ArrayList<Playlist> getAllPlaylistsByUserId(Long userId);
    Long createPlaylist(PlaylistDto playlistDto);
    void deletePlaylist(Long id);
    ArrayList<Playlist> searchPlaylists(String substring, Long userId);
    Long getPlaylistId(String substring);
}
