package run.itlife.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table
@Entity
public class ToDo implements Serializable {
    @Id
    @Column(name="todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;
    @Column(name = "todo_text")
    private String todoText;
    @Column(name = "is_complete")
    private Boolean isComplete;
    @Column(name = "deadline_at")
    private LocalDateTime deadlineAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getTodoId() {
        return todoId;
    }

    public void setTodoId(Long todoId) {
        this.todoId = todoId;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public LocalDateTime getDeadlineAt() {
        return deadlineAt;
    }

    public void setDeadlineAt(LocalDateTime deadlineAt) {
        this.deadlineAt = deadlineAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}