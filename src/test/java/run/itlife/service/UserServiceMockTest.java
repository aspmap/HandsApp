package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.dto.UserDto;
import run.itlife.entity.User;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockTest {
    @Mock
    UserService mockUserService;

    @Test
    public void findAll() {
        ArrayList<User> expectedData = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            User user = new User();
            user.setUsername("terminator " + i);
            user.setPassword("terminatorpassword");
            expectedData.add(user);
        }
        Mockito.when(mockUserService.findAll()).thenReturn(expectedData);
        List<User> resultData = mockUserService.findAll();
        Assert.assertEquals(expectedData.size(), resultData.size());
        Mockito.verify(mockUserService).findAll();
    }

    @Test
    public void findByUsername() {
        User user = new User();
        user.setUsername("terminator");
        user.setPassword("terminatorpassword");
        User expectedData;
        expectedData = user;
        Mockito.when(mockUserService.findByUsername(user.getUsername())).thenReturn(expectedData);
        User resultData = mockUserService.findByUsername(user.getUsername());
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(mockUserService).findByUsername(user.getUsername());
    }

    @Test
    public void getUsersOnly() {
        List<User> expectedData = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            User user = new User();
            user.setUsername("terminator " + i);
            user.setPassword("terminatorpassword");
            expectedData.add(user);
        }
        Mockito.when(mockUserService.findUsersOnly()).thenReturn(expectedData);
        List<User> resultData = mockUserService.findUsersOnly();
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(mockUserService).findUsersOnly();
    }

    @Test
    public void getUsersOnlyKey() {
        UserDto userDto = new UserDto();
        userDto.setUsername("terminator");
        userDto.setPassword("terminatorpassword");
        ArrayList<UserDto> expectedData = new ArrayList<>();
        expectedData.add(userDto);
        Mockito.when(mockUserService.findUsersOnlyKey(userDto.getUsername())).thenReturn(expectedData);
        ArrayList<UserDto> resultData = mockUserService.findUsersOnlyKey(userDto.getUsername());
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(mockUserService).findUsersOnlyKey(userDto.getUsername());
    }

    @Test
    public void searchUsers() {
        ArrayList<User> expectedData = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            User user = new User();
            user.setUsername("terminator " + i);
            user.setPassword("terminatorpassword");
            expectedData.add(user);
        }
        Mockito.when(mockUserService.findUsers("ter%")).thenReturn(expectedData);
        List<User> resultData = mockUserService.findUsers("ter%");
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(mockUserService).findUsers("ter%");
    }

    @Test
    public void searchGoogleUsers() {
        ArrayList<User> expectedData = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            User user = new User();
            user.setUsername("terminator " + i);
            user.setPassword("terminatorpassword");
            expectedData.add(user);
        }
        Mockito.when(mockUserService.findGoogleUsers("ter%")).thenReturn(expectedData);
        List<User> resultData = mockUserService.findGoogleUsers("ter%");
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(mockUserService).findGoogleUsers("ter%");
    }

    @Test
    public void countSearchUsers() {
        ArrayList<User> expectedData = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            User user = new User();
            user.setUsername("terminator " + i);
            user.setPassword("terminatorpassword");
            expectedData.add(user);
        }
        Mockito.when(mockUserService.countSearchUsers("ter")).thenReturn(expectedData.size());
        int resultData = mockUserService.countSearchUsers("ter");
        Assert.assertEquals(expectedData.size(), resultData);
        Mockito.verify(mockUserService).countSearchUsers("ter");
    }

    @Test
    public void countSearchGoogleUsers() {
        ArrayList<User> expectedData = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            User user = new User();
            user.setUsername("terminator " + i);
            user.setPassword("terminatorpassword");
            expectedData.add(user);
        }
        Mockito.when(mockUserService.countSearchGoogleUsers("ter")).thenReturn(expectedData.size());
        int resultData = mockUserService.countSearchGoogleUsers("ter");
        Assert.assertEquals(expectedData.size(), resultData);
        Mockito.verify(mockUserService).countSearchGoogleUsers("ter");
    }
}
