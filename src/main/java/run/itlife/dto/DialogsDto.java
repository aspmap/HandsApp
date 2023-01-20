package run.itlife.dto;

import run.itlife.entity.Messages;
import run.itlife.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class DialogsDto {

    private Long dialogId;
    private LocalDateTime createdAt;
    private Set<User> users;
    private List<Messages> messages;
    private Long userReceiveId;

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public Long getUserReceiveId() {
        return userReceiveId;
    }

    public void setUserReceiveId(Long userReceiveId) {
        this.userReceiveId = userReceiveId;
    }
}
