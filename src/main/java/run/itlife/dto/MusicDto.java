package run.itlife.dto;

import run.itlife.entity.User;

public class MusicDto {
    private Long musicId;
    private String fileName;
    private String projectName;
    private String songName;
    private String songYear;
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