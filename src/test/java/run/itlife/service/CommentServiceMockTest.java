package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import run.itlife.entity.Comment;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceMockTest {
    @Mock
    CommentService mockCommentService;

    @Test
    public void listAllBugs() {
        List<Comment> expectedData = new ArrayList<>();
        for (long i = 1; i < 3; i++) {
            Comment comment = new Comment();
            comment.setCommentId(i);
            comment.setCommentText("bug №" + i);
            expectedData.add(comment);
        }
        Mockito.when(mockCommentService.sortCommentsByDate(1L)).thenReturn(expectedData);
        List<Comment> commentsByDate = mockCommentService.sortCommentsByDate(1L);
        Assert.assertEquals(expectedData, commentsByDate);
        Mockito.verify(mockCommentService).sortCommentsByDate(1L);
    }
}
