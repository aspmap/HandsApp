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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class LikesRepositoryJpaTest {
    @Autowired
    private LikesRepository likesRepository;

    @Test
    public void countLikesByPostId() {
        int countLikesByPostId = this.likesRepository.countLikesByPostId(103L);
        Assert.assertEquals(1, countLikesByPostId);
    }

    @Test
    public void countLikesByUsername() {
        int countLikesByUsername = this.likesRepository.countLikesByUsername("shurrik77");
        Assert.assertEquals(1, countLikesByUsername);
    }

    @Test
    public void isLikePostForCurrentUser() {
        int isLikePostForCurrentUser = this.likesRepository.isLikePostForCurrentUser(16L,"shurrik77");
        Assert.assertEquals(1, isLikePostForCurrentUser);
    }
}
