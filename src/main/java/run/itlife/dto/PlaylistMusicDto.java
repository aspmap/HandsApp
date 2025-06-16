package run.itlife.dto;

import run.itlife.entity.Music;
import run.itlife.entity.Playlist;

public class PlaylistMusicDto {
    private Long playlistMusicId;
    private Music musicId;
    private Playlist playlistId;

    public Long getPlaylistMusicId() {
        return playlistMusicId;
    }

    public void setPlaylistMusicId(Long playlistMusicId) {
        this.playlistMusicId = playlistMusicId;
    }

    public Music getMusicId() {
        return musicId;
    }

    public void setMusicId(Music musicId) {
        this.musicId = musicId;
    }

    public Playlist getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Playlist playlistId) {
        this.playlistId = playlistId;
    }
}
