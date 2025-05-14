package run.itlife.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import run.itlife.controller.api.PostApiController;
import run.itlife.dto.CommentDto;
import run.itlife.dto.PostDto;
import run.itlife.service.PostService;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PostApiControllerMockTest {
    @Mock
    private PostService postService;
    @InjectMocks
    private PostApiController postApiController;
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private static PostDto post;
    private static long numberOfPost = 102L;
    private static String postJson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(postApiController)
                .build();
    }

    @Before
    public void init() throws JsonProcessingException {
        CommentDto commentDto = new CommentDto();
        commentDto.setPostId(1L);
        commentDto.setCommentText("Test comment");
        commentDto.setUsername("predator");
        commentDto.setCommentId(11L);
        ArrayList<CommentDto> comments = new ArrayList<>();
        comments.add(commentDto);
        post = new PostDto();
        post.setPostId(numberOfPost);
        post.setPhoto("152d7bb8-75ed-49bd-a634-0e3263c7e408.png");
        post.setExtFile("png");
        post.setContent("");
        post.setUsername("predator");
        post.setStorageType("S3");
        post.setCreatedAt(null);
        post.setUpdatedAt(null);
        post.setComments(comments);
        postJson = mapper.writeValueAsString(post);
    }

    @Test
    public void findAllPosts() throws Exception {
        mockMvc.perform(get("/api/post"))
                .andExpect(status().isOk());
    }

    @Test
    public void findPostById() throws Exception {
        when(postService.getAsDto(numberOfPost)).thenReturn(post);
        mockMvc.perform(get("/api/post/{id}", numberOfPost))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photo").value("152d7bb8-75ed-49bd-a634-0e3263c7e408.png"))
                .andExpect(jsonPath("$.extFile").value("png"))
                .andExpect(jsonPath("$.username").value("predator"));
        verify(postService, times(1)).getAsDto(numberOfPost);
    }

    @Test
    public void createPost() throws Exception {
        mockMvc.perform(post("/api/post/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePost() throws Exception {
        mockMvc.perform(put("/api/post/{id}", numberOfPost)
                .contentType(MediaType.APPLICATION_JSON)
                .content(postJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deletePost() throws Exception {
        mockMvc.perform(delete("/api/post/{id}", numberOfPost)
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
