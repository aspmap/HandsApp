package run.itlife;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.repository.PostRepository;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {
    @Mock
    PostRepository mockPostRepository;

    @Test
    public void countPostsOfUserVer1() {
        PostRepository localMockRepository = Mockito.mock(PostRepository.class);
        Mockito.when(localMockRepository.countPosts("robocop")).thenReturn(7);
        int postsCount = localMockRepository.countPosts("robocop");
        Assert.assertEquals(7, postsCount);
        Mockito.verify(localMockRepository).countPosts("robocop");
    }

    @Test
    public void countPostsOfUserVer2() {
        Mockito.when(mockPostRepository.countPosts("robocop")).thenReturn(7);
        int postsCount = mockPostRepository.countPosts("robocop");
        Assert.assertEquals(7, postsCount);
        Mockito.verify(mockPostRepository).countPosts("robocop");
    }

    @Test
    public void countMyLikesPosts() {
        Mockito.when(mockPostRepository.countMyLikesPosts("robocop")).thenReturn(200L);
        long myLikesPostsCount = mockPostRepository.countMyLikesPosts("robocop");
        Assert.assertEquals(200L, myLikesPostsCount);
        Mockito.verify(mockPostRepository).countMyLikesPosts("robocop");
    }
}
