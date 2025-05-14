package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.entity.Messages;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MessagesServiceMockTest {
    @Mock
    MessagesService messagesService;

    @Test
    public void listAllMessages() {
        ArrayList<Messages> expectedData = new ArrayList<>();
        for (long i = 1; i < 3; i++) {
            Messages messages = new Messages();
            messages.setMessageId(i);
            messages.setMessageFile("picture.jpg");
            messages.setMessageText("Message text");
            messages.setRead(true);
            expectedData.add(messages);
        }
        Mockito.when(messagesService.listAllMessages()).thenReturn(expectedData);
        List<Messages> allMessages = messagesService.listAllMessages();
        Assert.assertEquals(2, allMessages.size());
        Mockito.verify(messagesService).listAllMessages();
    }

    @Test
    public void findMessagesByDialogId() {
        ArrayList<Messages> expectedData = new ArrayList<>();
        for (long i = 1; i < 3; i++) {
            Messages messages = new Messages();
            messages.setMessageId(i);
            messages.setMessageFile("picture.jpg");
            messages.setMessageText("Message text");
            messages.setRead(true);
            expectedData.add(messages);
        }
        Mockito.when(messagesService.findMessagesByDialogId(3L)).thenReturn(expectedData);
        List<Messages> messagesByDialogId = messagesService.findMessagesByDialogId(3L);
        Assert.assertEquals(2, messagesByDialogId.size());
        Mockito.verify(messagesService).findMessagesByDialogId(3L);
    }

    @Test
    public void countMessagesInDialog() {
        Mockito.when(messagesService.countMessagesInDialog(3L)).thenReturn(2L);
        long countMessagesInDialog = messagesService.countMessagesInDialog(3L);
        Assert.assertEquals(2L, countMessagesInDialog);
        Mockito.verify(messagesService).countMessagesInDialog(3L);
    }

    @Test
    public void findUsersInDialog() {
        ArrayList<String> expectedData = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            expectedData.add("user " + i);
        }
        Mockito.when(messagesService.findUsersInDialog(3L)).thenReturn(expectedData);
        List<String> resultData = messagesService.findUsersInDialog(3L);
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(messagesService).findUsersInDialog(3L);
    }

    @Test
    public void getUserPhotoByUsername() {
        Mockito.when(messagesService.getUserPhotoByUsername("terminator")).thenReturn("photo.jpg");
        String userPhotoByUsername = messagesService.getUserPhotoByUsername("terminator");
        Assert.assertEquals("photo.jpg", userPhotoByUsername);
        Mockito.verify(messagesService).getUserPhotoByUsername("terminator");
    }

    @Test
    public void getUserEmailByUsername() {
        Mockito.when(messagesService.getUserEmailByUsername("terminator")).thenReturn("terminator@gmail.com");
        String uUserEmailByUsername = messagesService.getUserEmailByUsername("terminator");
        Assert.assertEquals("terminator@gmail.com", uUserEmailByUsername);
        Mockito.verify(messagesService).getUserEmailByUsername("terminator");
    }

    @Test
    public void getUserGoogleByUsername() {
        Mockito.when(messagesService.getUserGoogleByUsername("terminator")).thenReturn("true");
        String isIserGoogleByUsername = messagesService.getUserGoogleByUsername("terminator");
        Assert.assertEquals("true", isIserGoogleByUsername);
        Mockito.verify(messagesService).getUserGoogleByUsername("terminator");
    }

    @Test
    public void findUsersByDialogId() {
        ArrayList<String> expectedData = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            expectedData.add("user " + i);
        }
        Mockito.when(messagesService.findUsersByDialogId(3L)).thenReturn(expectedData);
        List<String> resultData = messagesService.findUsersByDialogId(3L);
        Assert.assertEquals(expectedData, resultData);
        Mockito.verify(messagesService).findUsersByDialogId(3L);
    }

}
