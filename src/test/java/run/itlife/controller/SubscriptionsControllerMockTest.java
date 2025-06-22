package run.itlife.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import run.itlife.service.SubscriptionsService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SubscriptionsControllerMockTest {
    @Mock
    private SubscriptionsService subscriptionsService;
    @InjectMocks
    private SubscriptionsController subscriptionsController;

    @Test
    void createSubscribeFromRecommendations() {
        assertEquals("redirect:/posts", subscriptionsController.createSubscribeFromRecommendations(null, "terminator"));
    }

    @Test
    void createSubscribe() {
        assertEquals("redirect:/posts_sub/{user}", subscriptionsController.createSubscribe("terminator"));
    }
}
