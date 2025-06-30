package run.itlife.service;

import run.itlife.dto.PlaylistDto;
import run.itlife.entity.Playlist;

import java.util.ArrayList;

public interface PlaylistService {
    ArrayList<Playlist> findAllPlaylistsByUserId(Long userId);
    Long createPlaylist(PlaylistDto playlistDto);
    void deletePlaylist(Long id);
    ArrayList<Playlist> findPlaylistsByUserId(Long userId);
    Long findPlaylistId(String substring, Long userId);
}
