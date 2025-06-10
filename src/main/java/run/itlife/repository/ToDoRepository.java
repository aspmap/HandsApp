package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.ToDo;
import run.itlife.entity.User;

import java.util.ArrayList;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    ArrayList<ToDo> findAllByUserOrderByIsComplete(User user);
}