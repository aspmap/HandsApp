package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class LikesServiceMockTest {
    @Mock
    LikesService likesService;

    @Test
    public void countLikesByPostId() {
        Mockito.when(likesService.countLikesByPostId(4L)).thenReturn(4);
        int countLikesByPostId = likesService.countLikesByPostId(4L);
        Assert.assertEquals(4, countLikesByPostId);
        Mockito.verify(likesService).countLikesByPostId(4);
    }

    @Test
    public void countLikesByUsername() {
        Mockito.when(likesService.countLikesByUsername("terminator")).thenReturn(4);
        int countLikesByUsername = likesService.countLikesByUsername("terminator");
        Assert.assertEquals(4, countLikesByUsername);
        Mockito.verify(likesService).countLikesByUsername("terminator");
    }

    @Test
    public void isLikePostForCurrentUser() {
        Mockito.when(likesService.isLikePostForCurrentUser(7, "terminator")).thenReturn(4);
        long isLikePostForCurrentUser = likesService.isLikePostForCurrentUser(7, "terminator");
        Assert.assertEquals(4, isLikePostForCurrentUser);
        Mockito.verify(likesService).isLikePostForCurrentUser(7, "terminator");
    }

}
