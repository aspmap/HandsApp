package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import run.itlife.dto.ToDoDto;
import run.itlife.entity.User;
import run.itlife.service.ToDoService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;

import static run.itlife.utils.Properties.Paths.*;

@Controller
public class ToDoController {
    @Autowired
    CommonsParams commonsParams;
    private final ToDoService toDoService;
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(ToDoController.class);

    @Autowired
    public ToDoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }

    @GetMapping("/todo")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findTodos(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("toDoAll", toDoService.findUnCompletedTasks(user.getUserId()));
        return "todo/todo";
    }

    @GetMapping("/todo/completed")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findTodosIsCompleted(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("toDoAll", toDoService.findCompletedTasks(user.getUserId()));
        return "todo/todo-completed";
    }

    @GetMapping("/todo/add")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createTodoTask(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "todo/add-todo";
    }

    @PostMapping("/todo/add")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createTodoTask(ToDoDto toDoDto, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long toDoId;
        toDoId = toDoService.createToDoTask(toDoDto);
        return "redirect:" + SEPARATOR + "todo";
    }

    @GetMapping("/todo/{toDoId}/complete")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void completeTodoTask(@PathVariable Long toDoId) {
        toDoService.completeTask(toDoId);
    }

    @DeleteMapping("/todo/delete/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteToDoTask(@PathVariable Long id) {
        toDoService.deleteTodo(id);
    }
}