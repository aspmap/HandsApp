package run.itlife.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import run.itlife.service.LikesService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LikesControllerMockTest {
    @Mock
    private LikesService likesService;
    @InjectMocks
    private LikesController likesController;

    @Test
    void create_like() {
        assertEquals("redirect:/post/{postId}", likesController.create_like(1L));
    }

    @Test
    void create_like_sub() {
        assertEquals("redirect:/post-view-sub/{postId}", likesController.create_like_sub(1L));
    }

    @Test
    void create_like_detail() {
        assertEquals("redirect:/posts_detail", likesController.create_like_detail(1L));
    }

    @Test
    void create_like_detail_sub() {
        assertEquals("redirect:/", likesController.create_like_detail_sub(1L));
    }

    @Test
    void create_like_detail_subuser() {
        assertEquals("redirect:/posts_detail_subuser/{user}", likesController.create_like_detail_subuser(1L, "terminator"));
    }
}
