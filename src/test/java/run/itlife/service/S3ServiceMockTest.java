package run.itlife.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

@RunWith(MockitoJUnitRunner.class)
public class S3ServiceMockTest {
    @Mock
    S3Service s3Service;

    @Test
    public void getUserGoogleByUsername() {
        Mockito.when(s3Service.uploadS3File("terminator", new File("testFile"))).thenReturn("photo.jpg");
        String s3File = s3Service.uploadS3File("terminator", new File("testFile"));
        Assert.assertEquals("photo.jpg", s3File);
        Mockito.verify(s3Service).uploadS3File("terminator", new File("testFile"));
    }
}
