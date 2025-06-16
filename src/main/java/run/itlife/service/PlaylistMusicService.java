package run.itlife.service;

import run.itlife.dto.PlaylistMusicDto;
import run.itlife.dto.WishlistPrivateDto;
import run.itlife.entity.Music;
import run.itlife.entity.Playlist;

import java.util.ArrayList;

public interface PlaylistMusicService {
    ArrayList<Music> getPlaylistByPlaylistId(Long playlistId);
    String getNamePlaylist(Long playlistId);
    void addToPlaylist(PlaylistMusicDto playlistMusicDto);
    void deleteFromPlaylist(Long playlistId, Long musicId);
}
