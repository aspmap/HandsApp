package run.itlife.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.itlife.controller.PostPaginationController;
import run.itlife.entity.Post;
import run.itlife.service.PostPaginationService;

@RestController
public class PostPaginationAPIController {
    private final PostPaginationService postPaginationService;
    private static final Logger log = LoggerFactory.getLogger(PostPaginationController.class);

    @Autowired
    public PostPaginationAPIController(PostPaginationService postPaginationService) {
        this.postPaginationService = postPaginationService;
    }

    @GetMapping("/pagination_api")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String getSubscribesPostsInfiniteScroll(ModelMap modelMap, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size, String sortBy) throws JsonProcessingException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> myDataPage = postPaginationService.findSubscribesPosts(username, pageable);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(myDataPage);
    }
}
