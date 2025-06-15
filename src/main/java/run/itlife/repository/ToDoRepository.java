package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.ToDo;
import run.itlife.entity.User;

import java.util.ArrayList;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    ArrayList<ToDo> findAllByUserOrderByIsComplete(User user);

    @Query(value = "select * from todo " +
            "where user_id = ? and is_complete = true ", nativeQuery = true)
    ArrayList<ToDo> findCompletedTasks(Long userId);

    @Query(value = "select * from todo " +
            "where user_id = ? and is_complete = false ", nativeQuery = true)
    ArrayList<ToDo> findUnCompletedTasks(Long userId);
}