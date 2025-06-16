package run.itlife.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Table
@Entity
public class Music {
    @Id
    @Column(name="music_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicId;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "project_name")
    private String projectName;
    @Column(name = "song_name")
    private String songName;
    @Column(name = "song_year")
    private String songYear;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongYear() {
        return songYear;
    }

    public void setSongYear(String songYear) {
        this.songYear = songYear;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}