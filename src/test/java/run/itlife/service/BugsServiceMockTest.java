package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.entity.Bugs;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BugsServiceMockTest {
    @Mock
    BugsService mockBugsService;

    @Test
    public void listAllBugs() {
        List<Bugs> expectedData = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            Bugs bugs = new Bugs();
            bugs.setUsername("bug №" + i);
            bugs.setBugText("bugpassword");
            bugs.setBugId(4L);
            expectedData.add(bugs);
        }
        Mockito.when(mockBugsService.findAllBugs()).thenReturn(expectedData);
        List<Bugs> allBugs = mockBugsService.findAllBugs();
        Assert.assertEquals(expectedData, allBugs);
        Mockito.verify(mockBugsService).findAllBugs();
    }
}
