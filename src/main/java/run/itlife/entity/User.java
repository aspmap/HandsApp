package run.itlife.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Маппинг сущностей с БД
@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @ManyToMany(mappedBy = "users")
    private Set<Dialogs> dialogs; //TODO Здесь List заменил на Set (жертвуя производительностью), т.к. приложение не запускалось и выдывало ошибку MultipleBagFetchException. В будущем доработать

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Messages> messages;

    @OneToMany(mappedBy = "userId")
    private List<Bugs> bugs;

    @OneToMany(mappedBy = "userLikeId")
    private List<Likes> userLike;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_google")
    private boolean isGoogle;

    @Column(name = "is_hidden")
    private boolean isHidden;

    @Column(name = "is_closed")
    private boolean isClosed;

    @Column(name = "last_visit")
    private LocalDateTime lastVisit;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

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


    public boolean getIsGoogle() {
        return isGoogle;
    }

    public void setIsGoogle(boolean google) {
        isGoogle = google;
    }

    public boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean closed) {
        isClosed = closed;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Set<Dialogs> getDialogs() {
        return dialogs;
    }

    public void setDialogs(Set<Dialogs> dialogs) {
        this.dialogs = dialogs;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Bugs> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bugs> bugs) {
        this.bugs = bugs;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User() {
        createdAt = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Likes> getUserLike() {
        return userLike;
    }

    public void setUserLike(List<Likes> userLike) {
        this.userLike = userLike;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return getIsActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    //Есть система прав, которая основана на ролях, а есть - на правах. Но в последнее время их слили в одно и то же.
    //И нам нужно возвращать Authority, но тем не менее мы будем их создавать на основе наших ролей.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .collect(Collectors.toList());
    }

	@Override
    //Возвращаем объект класса, реализующий UserDetails. Он вернёт нам Entity (он у нас как раз реализует UserDetails),
    //пароль мы у него посмотрим за счёт вызова метода getPassword() из User.java и getUsername.
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
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

    @Override
    public String toString() {
        return username;
    }

}