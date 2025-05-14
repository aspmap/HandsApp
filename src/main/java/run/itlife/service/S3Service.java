package run.itlife.service;

import java.io.File;

public interface S3Service {
    String uploadS3File(String username, File file);
}
