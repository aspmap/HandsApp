package run.itlife.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.dto.ToDoDto;
import run.itlife.entity.ToDo;
import run.itlife.entity.User;
import run.itlife.repository.ToDoRepository;
import run.itlife.repository.UserRepository;

import java.util.ArrayList;

@Service
@Transactional
public class ToDoServiceImpl implements ToDoService {
    private final ToDoRepository toDoRepository;
    private final UserRepository userRepository;

    public ToDoServiceImpl(ToDoRepository toDoRepository, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<ToDo> findAllByUser(User user) {
        return toDoRepository.findAllByUserOrderByIsComplete(user);
    }

    @Override
    public Long createToDoTask(ToDoDto toDoDto) {
        ToDo todo = new ToDo();
        todo.setTodoText(toDoDto.getTodoText());
        todo.setComplete(false);
        todo.setDeadlineAt(toDoDto.getDeadlineAt());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        todo.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        toDoRepository.save(todo);
        return todo.getTodoId();
    }

    @Override
    public void completeTask(Long toDoId) {
        ToDo todo = toDoRepository.findById(toDoId).orElseThrow();
        todo.setComplete(true);
        toDoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        toDoRepository.deleteById(id);
    }
}