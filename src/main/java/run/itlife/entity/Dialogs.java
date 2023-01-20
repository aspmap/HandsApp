package run.itlife.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Dialogs implements Serializable {

    @Id
    @Column(name="dialog_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dialogId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "name_dialog")
    private String nameDialog;

    @Column(name = "img_dialog")
    private String imgDialog;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_dialog",
            joinColumns = @JoinColumn(name = "dialog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    @OneToMany(mappedBy = "dialogs")
    private List<Messages> messages;

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public String getNameDialog() {
        return nameDialog;
    }

    public void setNameDialog(String nameDialog) {
        this.nameDialog = nameDialog;
    }

    public String getImgDialog() {
        return imgDialog;
    }

    public void setImgDialog(String imgDialog) {
        this.imgDialog = imgDialog;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Long getDialogId() {
        return dialogId;
    }

    public void setDialogId(Long dialogId) {
        this.dialogId = dialogId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Dialogs{" +
                "dialogId=" + dialogId +
                ", createdAt=" + createdAt +
                '}';
    }

}