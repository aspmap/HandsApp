package run.itlife.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface S3Service {

    String uploadS3File(String username, File file);

}
