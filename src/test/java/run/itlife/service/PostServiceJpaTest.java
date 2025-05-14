package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.config.JpaConfig;
import run.itlife.entity.Post;
import run.itlife.service.PostServiceImpl;

import java.util.List;

// TODO Не получилось разобраться пока. Ошибка: NullPointerException:
//  Cannot invoke "run.itlife.service.PostServiceImpl.findByUserName(String)" because "this.postServiceImpl" is null
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
//@Transactional
public class PostServiceJpaTest {
    //@Autowired
/*    private PostServiceImpl postServiceImpl;

    @Test
    public void findPostsByUserName() {
        List<Post> postsByUserName = this.postServiceImpl.findByUserName("altermuun");
        Assert.assertEquals(6, postsByUserName.size());
    }

    @Test
    public void countPostsOfUser() {
        int countPosts = this.postServiceImpl.countPosts("altermuun");
        Assert.assertEquals(6, countPosts);
    }

    @Test
    public void countCommentsByPost() {
        long countCommentsByPost = this.postServiceImpl.countComments(50L);
        Assert.assertEquals(1L, countCommentsByPost);
    }

    @Test
    public void sortedPostsByDate() {
        List<Post> sortedPostsByDate = this.postServiceImpl.sortedPostsByDate("altermuun");
        Assert.assertEquals(6, sortedPostsByDate.size());
    }

    @Test
    public void findSubscribesPostsByUserName() {
        List<Post> subscribesPostsByUserName = this.postServiceImpl.findSubscribesPosts("altermuun");
        Assert.assertEquals(3, subscribesPostsByUserName.size());
    }

    @Test
    public void countSubscribesPosts() {
        int countTags = this.postServiceImpl.countSubscribesPosts("altermuun");
        Assert.assertEquals(3, countTags);
    }

    @Test
    public void searchTags() {
        List<Post> tags = this.postServiceImpl.searchTags("%#veryimportantthings%");
        Assert.assertEquals(2, tags.size());
    }

    @Test
    public void countSearchTags() {
        long countSearchTags = this.postServiceImpl.countSearchTags("%#veryimportantthings%");
        Assert.assertEquals(2L, countSearchTags);
    }

    @Test
    public void findByContentLikeIgnoreCase() {
        List<Post> contentLikeIgnoreCase = this.postServiceImpl.search("Very");
        Assert.assertEquals(2, contentLikeIgnoreCase.size());
    }

    @Test
    public void isLikePostByUserName() {
        List<Long> likePostByUserName = this.postServiceImpl.isLikePost("altermuun");
        Assert.assertEquals(2, likePostByUserName.size());
    }

    @Test
    public void selectMyLikesPostsByUserName() {
        List<Post> myLikesPostsByUserName = this.postServiceImpl.selectMyLikesPosts("altermuun");
        Assert.assertEquals(2, myLikesPostsByUserName.size());
    }

    @Test
    public void countMyLikesPostsOfUser() {
        long countMyLikesPosts = this.postServiceImpl.countMyLikesPosts("altermuun");
        Assert.assertEquals(2, countMyLikesPosts);
    }*/
}
