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
    void subscribe_from_recommendations() {
        assertEquals("redirect:/posts", subscriptionsController.subscribe_from_recommendations(null, "terminator"));
    }

    @Test
    void subscribe() {
        assertEquals("redirect:/sub-posts/{user}", subscriptionsController.subscribe("terminator"));
    }
}
