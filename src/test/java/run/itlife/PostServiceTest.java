package run.itlife;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.service.PostService;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {
    @Mock
    PostService mockPostService;

    @Test
    public void countPostsOfUserVer1() {
        PostService localMockRepository = Mockito.mock(PostService.class);
        Mockito.when(localMockRepository.countPosts("robocop")).thenReturn(7);
        int postsCount = localMockRepository.countPosts("robocop");
        Assert.assertEquals(7, postsCount);
        Mockito.verify(localMockRepository).countPosts("robocop");
    }

    @Test
    public void countPostsOfUserVer2() {
        Mockito.when(mockPostService.countPosts("robocop")).thenReturn(7);
        int postsCount = mockPostService.countPosts("robocop");
        Assert.assertEquals(7, postsCount);
        Mockito.verify(mockPostService).countPosts("robocop");
    }

    @Test
    public void countMyLikesPosts() {
        Mockito.when(mockPostService.countMyLikesPosts("robocop")).thenReturn(200L);
        long myLikesPostsCount = mockPostService.countMyLikesPosts("robocop");
        Assert.assertEquals(200L, myLikesPostsCount);
        Mockito.verify(mockPostService).countMyLikesPosts("robocop");
    }
}
