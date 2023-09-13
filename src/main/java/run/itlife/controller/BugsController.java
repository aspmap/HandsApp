package run.itlife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import run.itlife.config.KafkaConfig;
import run.itlife.controller.api.kafka.Sender;
import run.itlife.dto.BugsDto;
import run.itlife.entity.User;
import run.itlife.service.BugsService;
import run.itlife.service.UserService;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;

@Controller
public class BugsController {
    private final BugsService bugsService;
    private final UserService userService;

    @Autowired
    public BugsController(BugsService bugsService, UserService userService) {
        this.bugsService = bugsService;
        this.userService = userService;
    }

    @PostMapping("/bug/newKafka")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String postNewBugKafka(BugsDto bugsDto, ModelMap modelMap) throws IOException {

        //Подготавливаем данные для отправки в Кафку
        setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userId = userService.findByUsername(username);
        bugsDto.setBugText(bugsDto.getBugText());
        bugsDto.setUserId(userId.getUserId());
        bugsDto.setCreatedAt(LocalDateTime.now());
        bugsDto.setCreatedAtText(bugsDto.getCreatedAt().toString());
        bugsDto.setUsername(username);

        //Делаем сериализацию в JSON
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(writer, bugsDto);
        String result = writer.toString();

        //Генерим рандомный ключ
        int key = (int) (Math.random() * (1000 + 1));
        Integer keyData = key;

        //Отправляем данные в Кафку
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaConfig.class);
            context.getBean(Sender.class).sendMsg(result, keyData);
            return "messages-templates/message-send";
        } catch (KafkaException ke) {
            return "messages-templates/errorkafka";
        }
    }

    @KafkaListener(id = "listen-feedback", topics = "feedback")
    public void listen1(String in) throws InterruptedException, IOException {
        StringReader reader = new StringReader(in);
        ObjectMapper mapper = new ObjectMapper();
        BugsDto bugsDto = mapper.readValue(reader, BugsDto.class);
        bugsService.createFromKafka(bugsDto);
    }

    @GetMapping("/bug/new")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String bugNew(ModelMap modelMap) {
        setCommonParams(modelMap);
        return "bugs/bugs-add";
    }

    @PostMapping("/bug/new")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String postNewBug(BugsDto bugsDto, ModelMap modelMap) {
        setCommonParams(modelMap);
        bugsService.create(bugsDto);
        return "messages-templates/message-send";
    }

    @GetMapping("/bugs_view")
    @PreAuthorize("hasRole('ADMIN')")
    public String index(ModelMap modelMap) {
        modelMap.put("bugs", bugsService.listAllBugs());
        modelMap.put("userslist", userService.findAll());
        setCommonParams(modelMap);
        return "bugs/bugs";
    }

    private void setCommonParams(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        modelMap.put("user", username);
        modelMap.put("userinfo", userService.findByUsername(username));
        modelMap.put("userOnlyList", userService.getUsersOnly());
        modelMap.put("usersOnlyKey", userService.getUsersOnlyKey(username));
    }

}