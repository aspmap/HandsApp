package run.itlife.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

@RestController
@RequestMapping("api/kafka")
public class BugsApiController {
    private final BugsService bugsService;
    private final UserService userService;

    @Autowired
    public BugsApiController(BugsService bugsService, UserService userService) {
        this.bugsService = bugsService;
        this.userService = userService;
    }

    @PostMapping("sendMsgToKafka")
    @ResponseStatus(HttpStatus.CREATED)
    public String createMsgToKafka() throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaConfig.class);

        //Подготавливаем данные для отправки в Кафку
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userId = userService.findByUsername(username);
        BugsDto bugsDto = new BugsDto();
        bugsDto.setBugText("Test of Kafka");
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
        int key = (int) (Math.random()*(1000+1));
        Integer keyData = key;

        //Отправляем данные в Кафку
        context.getBean(Sender.class).sendMsg(result, keyData);
        return "Message #" + keyData + " sent to Kafka successfully. Message content: " + result;
    }

}
