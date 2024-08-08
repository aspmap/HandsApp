package run.itlife.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import run.itlife.utils.SaveFile;

import java.io.File;

@Service
public class S3ServiceImpl implements S3Service {

    @Value("${application.bucket.name}")
    private String bucketName;

    public static final String S3_ADDRESS = "storage.yandexcloud.net";

    @Autowired
    private AmazonS3 s3Client;

    public String uploadS3File(String username, File file) {
        String fileName = "img" + SaveFile.SEPARATOR + "users" + SaveFile.SEPARATOR + username + SaveFile.SEPARATOR + file.getName();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
        file.delete();
        return fileName;
    }

}