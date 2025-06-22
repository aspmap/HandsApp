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
    void createLike() {
        assertEquals("redirect:/post_view/{postId}", likesController.createLike(1L));
    }

    @Test
    void createLikeSub() {
        assertEquals("redirect:/post-view-sub/{postId}", likesController.createLikeSub(1L));
    }

    @Test
    void createLikeDetail() {
        assertEquals("redirect:/posts_detail", likesController.createLikeDetail(1L));
    }

    @Test
    void createLikeDetailSub() {
        assertEquals("redirect:/", likesController.createLikeDetailSub(1L));
    }

    @Test
    void createLikeDetailSubuser() {
        assertEquals("redirect:/posts_detail_subuser/{user}", likesController.createLikeDetailSubuser(1L, "terminator"));
    }
}
