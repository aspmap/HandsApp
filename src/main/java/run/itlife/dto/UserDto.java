package run.itlife.dto;

import run.itlife.entity.Dialogs;

import java.util.Set;

public class UserDto {

    private Long userId;
    private String username;
    private String password;
    private String surname;
    private String firstname;
    private String photo;
    private String info;
    private String www;
    private String email;
    private String phone;
    private String sex;
    private Set<Dialogs> dialogs;
    private String isSub;

    private String isGoogle;

    public String getIsGoogle() {
        return isGoogle;
    }

    public void setIsGoogle(String isGoogle) {
        this.isGoogle = isGoogle;
    }

    public Set<Dialogs> getDialogs() {
        return dialogs;
    }

    public void setDialogs(Set<Dialogs> dialogs) {
        this.dialogs = dialogs;
    }

    public String getIsSub() {
        return isSub;
    }

    public void setIsSub(String isSub) {
        this.isSub = isSub;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}