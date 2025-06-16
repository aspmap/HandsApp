package run.itlife.dto;

import run.itlife.entity.Music;
import run.itlife.entity.User;

public class PlaylistDto {
    private Long playlistId;
    private String playlistName;
    private User user;

    public Long getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Long playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
