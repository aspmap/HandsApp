package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import run.itlife.dto.ToDoDto;
import run.itlife.entity.User;
import run.itlife.service.ToDoService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

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
    public String todos(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("toDoAll", toDoService.findUnCompletedTasks(user.getUserId()));
        return "todo/todo";
    }

    @GetMapping("/todo/completed")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String todosCompleted(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("toDoAll", toDoService.findCompletedTasks(user.getUserId()));
        return "todo/todo-completed";
    }

    @GetMapping("/todo/add")
    @PreAuthorize("hasRole('USER')")
    public String newTask(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "todo/add-todo";
    }

    @PostMapping("/todo/add")
    @PreAuthorize("hasRole('USER')")
    public String addNewTask(ToDoDto toDoDto, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        commonsParams.setCommonParams(modelMap);
        Long toDoId;
        toDoId = toDoService.createToDoTask(toDoDto);
        return "redirect:" + SaveFile.SEPARATOR + "todo";
    }

    @GetMapping("/todo/{toDoId}/complete")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void completeTask(@PathVariable Long toDoId) {
        toDoService.completeTask(toDoId);
    }

    @PostMapping("/todo/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteToDo(@PathVariable Long id) {
        toDoService.deleteTodo(id);
    }
}