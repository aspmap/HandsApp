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

import java.util.List;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional*/
public class HandshakeRepositoryJpaTest {
   /* @Autowired
    private HandshakeRepository handshakeRepository;

    @Test
    public void selectUsersId() {
        List<Integer> usersId = this.handshakeRepository.findUsersId(4);
        Assert.assertEquals(8, usersId.size());
    }

    @Test
    public void findUsersById() {
        List<String> users = this.handshakeRepository.findUsersById(4);
        Assert.assertEquals(1, users.size());
    }*/
}
