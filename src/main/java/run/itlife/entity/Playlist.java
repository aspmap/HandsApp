package run.itlife.entity;

import javax.persistence.*;

@Table
@Entity
public class Playlist {
    @Id
    @Column(name="playlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;
    @Column(name = "playlist_name")
    private String playlistName;
    @ManyToOne
    @JoinColumn(name = "user_id")
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
