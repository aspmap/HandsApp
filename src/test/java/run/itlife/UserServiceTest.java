package run.itlife;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.repository.UserRepository;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserRepository mockUserRepository;

    @Test
    public void getUsersOnlyKey() {
        ArrayList<String> expectedData = new ArrayList<>();
        expectedData.add("1");
        expectedData.add("2");
        Mockito.when(mockUserRepository.getUsersOnlyKey("robocop")).thenReturn(expectedData);
        ArrayList<String> resultData = mockUserRepository.getUsersOnlyKey("robocop");
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(mockUserRepository).getUsersOnlyKey("robocop");
    }
}
