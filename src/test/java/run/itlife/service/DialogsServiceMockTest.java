package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.entity.Dialogs;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DialogsServiceMockTest {
    @Mock
    DialogsService dialogsService;

    @Test
    public void findById() {
        Dialogs dialogs = new Dialogs();
        dialogs.setDialogId(4L);
        dialogs.setNameDialog("Dialog name");
        dialogs.setImgDialog("picture.jpg");
        Mockito.when(dialogsService.findById(4L)).thenReturn(dialogs);
        Dialogs dialogsById = dialogsService.findById(4L);
        Assert.assertEquals(dialogs, dialogsById);
        Mockito.verify(dialogsService).findById(4L);
    }

    @Test
    public void checkDuplicateDialogues() {
        Mockito.when(dialogsService.checkDuplicateDialogues("terminator", "phantasm")).thenReturn((byte) 2);
        byte duplicateDialogues = dialogsService.checkDuplicateDialogues("terminator", "phantasm");
        Assert.assertEquals(2, duplicateDialogues);
        Mockito.verify(dialogsService).checkDuplicateDialogues("terminator", "phantasm");
    }

    @Test
    public void getDialogIdByUsers() {
        Mockito.when(dialogsService.getDialogIdByUsers("terminator", "phantasm")).thenReturn(2L);
        long duplicateDialogues = dialogsService.getDialogIdByUsers("terminator", "phantasm");
        Assert.assertEquals(2L, duplicateDialogues);
        Mockito.verify(dialogsService).getDialogIdByUsers("terminator", "phantasm");
    }
}
