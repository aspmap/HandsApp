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
import run.itlife.entity.Dialogs;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class DialogsRepositoryJpaTest {
    @Autowired
    private DialogsRepository dialogsRepository;

    @Test
    public void checkDuplicateDialogues() {
        byte duplicateDialogues = this.dialogsRepository.checkDuplicateDialogues("terminator", "phantasm");
        Assert.assertEquals(1, duplicateDialogues);
    }

    @Test
    public void getDialogIdByUsers() {
        long dialogIdByUsers = this.dialogsRepository.getDialogIdByUsers("terminator", "phantasm");
        Assert.assertEquals(9, dialogIdByUsers);
    }
}
