package run.itlife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
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
import run.itlife.utils.CommonsParams;
import static run.itlife.utils.Properties.ErrorMessages.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;

@Controller
public class BugsController {
    private final BugsService bugsService;
    private final UserService userService;
    @Autowired
    CommonsParams commonsParams;
    private static final Logger log = LoggerFactory.getLogger(BugsController.class);

    @Autowired
    public BugsController(BugsService bugsService, UserService userService) {
        this.bugsService = bugsService;
        this.userService = userService;
    }

    @PostMapping("/bug/create_bug_kafka")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createBugKafka(BugsDto bugsDto, ModelMap modelMap) throws IOException {

        //Подготавливаем данные для отправки в Кафку
        commonsParams.setCommonParams(modelMap);
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
            log.error(ERROR + ke);
            return "messages-templates/errorkafka";
        }
    }

    @KafkaListener(id = "listen-feedback", topics = "feedback")
    public void listen1(String in) throws IOException {
        StringReader reader = new StringReader(in);
        ObjectMapper mapper = new ObjectMapper();
        BugsDto bugsDto = mapper.readValue(reader, BugsDto.class);
        bugsService.createBugReportFromKafka(bugsDto);
    }

    @GetMapping("/bug/create")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createBug(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "bugs/create-bug";
    }

    @PostMapping("/bug/create")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String createBug(BugsDto bugsDto, ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        bugsService.createBugReport(bugsDto);
        return "messages-templates/message-send";
    }

    @GetMapping("/bugs_view")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewBugs(ModelMap modelMap) {
        modelMap.put("bugs", bugsService.findAllBugs());
        modelMap.put("userslist", userService.findAll());
        commonsParams.setCommonParams(modelMap);
        return "bugs/bugs";
    }
}