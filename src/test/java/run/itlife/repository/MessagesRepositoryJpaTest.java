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
import run.itlife.entity.Messages;

import java.util.List;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional*/
public class MessagesRepositoryJpaTest {
   /* @Autowired
    private MessagesRepository messagesRepository;

    @Test
    public void findMessagesByDialogId() {
        List<Messages> messagesByDialogId = this.messagesRepository.findMessagesByDialogId(9L);
        Assert.assertEquals(2, messagesByDialogId.size());
    }

    @Test
    public void findUsersByDialogId() {
        List<String> usersByDialogId = this.messagesRepository.findUsersByDialogId(9L);
        Assert.assertEquals(2, usersByDialogId.size());
    }

    @Test
    public void countMessagesInDialog() {
        long countMessagesInDialog = this.messagesRepository.countMessagesInDialog(9L);
        Assert.assertEquals(2, countMessagesInDialog);
    }

    @Test
    public void findUsersInDialog() {
        List<String> usersInDialog = this.messagesRepository.findUsersInDialog(9L);
        Assert.assertEquals(2, usersInDialog.size());
    }

    @Test
    public void findDialogIdByUsersOwner() {
        List<String> dialogIdByUsersOwner = this.messagesRepository.findDialogIdByUsersOwner("terminator", "phantasm");
        Assert.assertEquals(1, dialogIdByUsersOwner.size());
    }

    @Test
    public void findUserPhotoByUsername() {
        String userPhotoByUsername = this.messagesRepository.findUserPhotoByUsername("terminator");
        Assert.assertEquals("bc34a420-286b-460b-9d7e-af400f4f74b2.jpg", userPhotoByUsername);
    }

    @Test
    public void findUserEmailByUsername() {
        String userEmailByUsername = this.messagesRepository.findUserEmailByUsername("terminator");
        Assert.assertEquals("", userEmailByUsername);
    }

    @Test
    public void findUserGoogleByUsername() {
        String userGoogleByUsername = this.messagesRepository.findUserGoogleByUsername("terminator");
        Assert.assertEquals("false", userGoogleByUsername);
    }*/
}
