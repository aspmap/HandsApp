package run.itlife.service;

import run.itlife.dto.PlaylistMusicDto;
import run.itlife.entity.Music;

import java.util.ArrayList;

public interface PlaylistMusicService {
    ArrayList<Music> findPlaylistByPlaylistId(Long playlistId);
    String findNamePlaylist(Long playlistId);
    void addToPlaylist(PlaylistMusicDto playlistMusicDto);
    void deleteMusicFromPlaylist(Long playlistId, Long musicId);
}
