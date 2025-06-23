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
import run.itlife.entity.User;

import java.util.ArrayList;
import java.util.List;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional*/
public class UserRepositoryJpaTest {
 /*   @Autowired
    private UserRepository userRepository;

    @Test
    public void findUsersOnly() {
        List<User> usersOnly = this.userRepository.findUsersOnly();
        Assert.assertEquals(5, usersOnly.size());
    }

    @Test
    public void findUsersOnlyKey() {
        ArrayList<String> usersOnlyKey = this.userRepository.findUsersOnlyKey("terminator");
        Assert.assertEquals(5, usersOnlyKey.size());
    }

    @Test
    public void findUsers() {
        List<User> users = this.userRepository.findUsers("shu%");
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void findGoogleUsers() {
        List<User> users = this.userRepository.findGoogleUsers("pav%");
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void countSearchUsers() {
        int countSearchUsers = this.userRepository.countSearchUsers("shu%");
        Assert.assertEquals(2, countSearchUsers);
    }

    @Test
    public void countSearchGoogleUsers() {
        int countSearchGoogleUsers = this.userRepository.countSearchGoogleUsers("pav%");
        Assert.assertEquals(1, countSearchGoogleUsers);
    }*/

}
