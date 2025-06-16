package run.itlife.entity;

import javax.persistence.*;

@Table(name = "playlist_music")
@Entity
public class PlaylistMusic {
    @Id
    @Column(name="playlist_music_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistMusicId;
    @ManyToOne
    @JoinColumn(name = "music_id")
    private Music musicId;
    @ManyToOne
    @JoinColumn(name = "playlist_id")
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
