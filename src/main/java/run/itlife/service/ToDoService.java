package run.itlife.service;

import run.itlife.dto.ToDoDto;
import run.itlife.entity.ToDo;
import run.itlife.entity.User;

import java.util.ArrayList;

public interface ToDoService {
    ArrayList<ToDo> findAllByUser(User user);
    Long createToDoTask(ToDoDto toDoDto);
    void completeTask(Long toDoId);
    void deleteTodo(Long id);
}