package run.itlife.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.config.JpaConfig;
import run.itlife.entity.Post;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class PostRepositoryJpaTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    public void findPostsByUserName() {
        List<Post> postsByUserName = this.postRepository.findByUserName("altermuun");
        Assert.assertEquals(6, postsByUserName.size());
    }

    @Test
    public void countPostsOfUser() {
        int countPosts = this.postRepository.countPosts("altermuun");
        Assert.assertEquals(6, countPosts);
    }

    @Test
    public void countCommentsByPost() {
        long countCommentsByPost = this.postRepository.countComments(50L);
        Assert.assertEquals(1L, countCommentsByPost);
    }

    @Test
    public void findSortedPostsByDate() {
        List<Post> sortedPostsByDate = this.postRepository.findSortedPostsByDate("altermuun");
        Assert.assertEquals(6, sortedPostsByDate.size());
    }

    @Test
    public void findSubscribesPostsByUserName() {
        List<Post> subscribesPostsByUserName = this.postRepository.findSubscribesPosts("altermuun");
        Assert.assertEquals(3, subscribesPostsByUserName.size());
    }

    @Test
    public void countSubscribesPosts() {
        int countTags = this.postRepository.countSubscribesPosts("altermuun");
        Assert.assertEquals(3, countTags);
    }

    @Test
    public void searchTags() {
        List<Post> tags = this.postRepository.findTags("%#veryimportantthings%");
        Assert.assertEquals(2, tags.size());
    }

    @Test
    public void countSearchTags() {
        long countSearchTags = this.postRepository.countSearchTags("%#veryimportantthings%");
        Assert.assertEquals(2L, countSearchTags);
    }

    @Test
    public void findByContentLikeIgnoreCase() {
        List<Post> contentLikeIgnoreCase = this.postRepository.findByContentLikeIgnoreCase("%Very%");
        Assert.assertEquals(2, contentLikeIgnoreCase.size());
    }

    @Test
    public void isLikePostByUserName() {
        List<Long> likePostByUserName = this.postRepository.isLikePost("altermuun");
        Assert.assertEquals(2, likePostByUserName.size());
    }

    @Test
    public void findMyLikesPostsByUserName() {
        List<Post> myLikesPostsByUserName = this.postRepository.findMyLikesPosts("altermuun");
        Assert.assertEquals(2, myLikesPostsByUserName.size());
    }

    @Test
    public void countMyLikesPostsOfUser() {
        long countMyLikesPosts = this.postRepository.countMyLikesPosts("altermuun");
        Assert.assertEquals(2, countMyLikesPosts);
    }
}
