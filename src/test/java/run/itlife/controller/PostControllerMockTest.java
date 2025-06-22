package run.itlife.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import run.itlife.config.WebConfig;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
@RunWith(MockitoJUnitRunner.class)
public class PostControllerMockTest {
    MockMvc mockMvc;
    @InjectMocks
    private PostController postController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(postController)
                .build();
    }

    // TODO Ругается на сервисы и авторизацию в setCommonParams(modelMap)
    @Test
    public void postNewImage() throws Exception {
/*
        MvcResult result = mockMvc.perform(get("/post/new_image"))
                .andReturn();
        String returnResult = result.getResponse().getForwardedUrl();
        Assert.assertEquals("posts/post-new-img", returnResult);*/
    }
}
