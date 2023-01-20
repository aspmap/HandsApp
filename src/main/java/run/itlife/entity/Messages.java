package run.itlife.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Messages {

    @Id
    @Column(name="message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(name="message_text")
    private String messageText;

    @Column(name="message_file")
    private String messageFile;

    @Column(name="is_read")
    private boolean isRead;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dialog_id")
    private Dialogs dialogs;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Dialogs getDialogs() {
        return dialogs;
    }

    public void setDialogs(Dialogs dialogs) {
        this.dialogs = dialogs;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageFile() {
        return messageFile;
    }

    public void setMessageFile(String messageFile) {
        this.messageFile = messageFile;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
