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
import run.itlife.entity.Subscriptions;

import java.util.List;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional*/
public class SubscriptionsRepositoryJpaTest {
 /*   @Autowired
    private SubscriptionsRepository subscriptionsRepository;

    @Test
    public void findSubscribes() {
        List<Subscriptions> subscribes = this.subscriptionsRepository.findSubscribes("terminator");
        Assert.assertEquals(2, subscribes.size());
    }

    @Test
    public void findSubscribers() {
        List<Subscriptions> subscribers = this.subscriptionsRepository.findSubscribers("terminator");
        Assert.assertEquals(1, subscribers.size());
    }

    @Test
    public void isSubscribe() {
        int isSubscribe = this.subscriptionsRepository.isSubscribe("terminator", "phantasm");
        Assert.assertEquals(1, isSubscribe);
    }

    @Test
    public void countSubscribe() {
        int countSubscribe = this.subscriptionsRepository.countSubscribe("terminator");
        Assert.assertEquals(2, countSubscribe);
    }

    @Test
    public void countSubscribers() {
        int countSubscribers = this.subscriptionsRepository.countSubscribers("terminator");
        Assert.assertEquals(1, countSubscribers);
    }*/
}
