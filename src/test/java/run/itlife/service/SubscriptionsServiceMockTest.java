package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.entity.Messages;
import run.itlife.entity.Subscriptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SubscriptionsServiceMockTest {
    @Mock
    SubscriptionsService subscriptionsService;

    @Test
    public void findSubscribes() {
        ArrayList<Subscriptions> expectedData = new ArrayList<>();
        for (long i = 1; i < 3; i++) {
            Subscriptions subscriptions = new Subscriptions();
            subscriptions.setSubId(i);
            expectedData.add(subscriptions);
        }
        Mockito.when(subscriptionsService.findSubscribes("terminator")).thenReturn(expectedData);
        List<Subscriptions> subscribes = subscriptionsService.findSubscribes("terminator");
        Assert.assertEquals(2, subscribes.size());
        Mockito.verify(subscriptionsService).findSubscribes("terminator");
    }

    @Test
    public void findSubscribers() {
        ArrayList<Subscriptions> expectedData = new ArrayList<>();
        for (long i = 1; i < 3; i++) {
            Subscriptions subscriptions = new Subscriptions();
            subscriptions.setSubId(i);
            expectedData.add(subscriptions);
        }
        Mockito.when(subscriptionsService.findSubscribers("terminator")).thenReturn(expectedData);
        List<Subscriptions> subscribes = subscriptionsService.findSubscribers("terminator");
        Assert.assertEquals(2, subscribes.size());
        Mockito.verify(subscriptionsService).findSubscribers("terminator");
    }

    @Test
    public void isSubscribe() {
        Mockito.when(subscriptionsService.isSubscribe("terminator", "phantasm")).thenReturn(7);
        int isSubscribe = subscriptionsService.isSubscribe("terminator", "phantasm");
        Assert.assertEquals(7, isSubscribe);
        Mockito.verify(subscriptionsService).isSubscribe("terminator", "phantasm");
    }

    @Test
    public void countSubscribe() {
        Mockito.when(subscriptionsService.countSubscribe("terminator")).thenReturn(7);
        int countSubscribe = subscriptionsService.countSubscribe("terminator");
        Assert.assertEquals(7, countSubscribe);
        Mockito.verify(subscriptionsService).countSubscribe("terminator");
    }

    @Test
    public void countSubscribers() {
        Mockito.when(subscriptionsService.countSubscribers("terminator")).thenReturn(7);
        int countSubscribers = subscriptionsService.countSubscribers("terminator");
        Assert.assertEquals(7, countSubscribers);
        Mockito.verify(subscriptionsService).countSubscribers("terminator");
    }
}
