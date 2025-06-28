package run.itlife.controller;

import org.junit.Test;
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
        assertEquals("redirect:/post_view_of_subscriber/{postId}", likesController.createLikeInSubscribers(1L));
    }

    @Test
    void createLikeDetail() {
        assertEquals("redirect:/posts_detail", likesController.createLikeInPostsDetail(1L));
    }

    @Test
    void createLikeDetailSub() {
        assertEquals("redirect:/", likesController.createLikeInPostsDetail(1L));
    }

    @Test
    void createLikeDetailSubuser() {
        assertEquals("redirect:/create_comment_in_posts_detail_of_subscriber/{user}", likesController.createLikeDetailInSubscriber(1L, "terminator"));
    }
}
