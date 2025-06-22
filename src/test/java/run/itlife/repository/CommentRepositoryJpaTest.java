package run.itlife.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.config.JpaConfig;
import run.itlife.entity.Comment;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class CommentRepositoryJpaTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void sortCommentsByDate() {
        List<Comment> commentsByDate = this.commentRepository.findSortedCommentsByDate(107L);
        Assert.assertEquals(7, commentsByDate.size());
    }
}
