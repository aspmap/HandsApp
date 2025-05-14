package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.dto.UserDto;
import run.itlife.service.UserService;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockTest {
    @Mock
    UserService mockUserService;

    @Test
    public void getUsersOnlyKey() {
        UserDto userDto = new UserDto();
        userDto.setUsername("terminator");
        userDto.setPassword("terminatorpassword");
        ArrayList<UserDto> expectedData = new ArrayList<>();
        expectedData.add(userDto);
        Mockito.when(mockUserService.getUsersOnlyKey("robocop")).thenReturn(expectedData);
        ArrayList<UserDto> resultData = mockUserService.getUsersOnlyKey("robocop");
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(mockUserService).getUsersOnlyKey("robocop");
    }
}
