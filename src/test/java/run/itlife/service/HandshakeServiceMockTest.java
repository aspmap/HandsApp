package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class HandshakeServiceMockTest {
    @Mock
    HandshakeService handshakeService;

    @Test
    public void searchUsers() {
        ArrayList<Integer> expectedData = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            expectedData.add(i);
        }
        Mockito.when(handshakeService.findUsersId(1)).thenReturn(expectedData);
        ArrayList<Integer> resultData = handshakeService.findUsersId(1);
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(handshakeService).findUsersId(1);
    }

    @Test
    public void findUsersById() {
        ArrayList<String> expectedData = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            expectedData.add("user " + i);
        }
        Mockito.when(handshakeService.findUsersById(1)).thenReturn(expectedData);
        List<String> resultData = handshakeService.findUsersById(1);
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(handshakeService).findUsersById(1);
    }
}
