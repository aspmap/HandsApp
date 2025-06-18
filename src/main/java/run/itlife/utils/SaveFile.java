package run.itlife.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static run.itlife.enums.FileExtensions.*;
import static run.itlife.enums.FileExtensions.PNG;
import static run.itlife.utils.EditImage.resizeImage;
import static run.itlife.utils.OtherUtils.generateFileName;

public class SaveFile {
    private static final String PATH_VIDEO_USERS = "/resources/video/users/";
    private static final String PATH_IMAGE_USERS = "/resources/img/users/";
    public static final String PATH_MUSIC_USERS = "/resources/music/users/";
    public static final String SEPARATOR = "/";
    private static final String COMMA = ",";
    public static final String POINT = ".";
    private static final int IMAGE_WIDTH = 500;
    private static final int IMAGE_HEIGHT = 500;
    private static final int MAX_UPLOAD_FILE_SIZE_IN_MB = 20 * 24 * 24; // 20 МБ


    public Map<String, String> saveFile(String username, ServletContext context, MultipartFile file) throws IOException {
        Map<String, String> filenameMap = new HashMap<>();
        String extension;

        if (file.getContentType() != null) {
            switch (file.getContentType()) {
                case "video/mp4":
                    extension = MP4.getExtension();
                    break;
                case "video/quicktime":
                    extension = MOV.getExtension();
                    break;
                default:
                    extension = MP4.getExtension();
                    break;
            }
            String filename = generateFileName() + POINT + extension;
            File dir = new File(context.getRealPath(PATH_VIDEO_USERS + username));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // TODO написать логику обрезки видео
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(dir + SEPARATOR + filename)));
            stream.write(bytes);
            stream.close();
            filenameMap.put(filename, extension);
            return filenameMap;
        }
        return null;
    }

    public String saveFile(String username, ServletContext context, String file) throws IOException {
        String base64Image = file.split(COMMA)[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

        if (imageBytes.length > MAX_UPLOAD_FILE_SIZE_IN_MB) {
            return null;
        }
        String filename = generateFileName() + POINT + PNG.getExtension();
        File dir = new File(context.getRealPath(PATH_IMAGE_USERS + username)); // TODO PATH_VIDEO_USERS вынести в аргументы функции
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File uploadedFile = new File(dir + SEPARATOR + filename); // TODO PATH_VIDEO_USERS вынести в аргументы функции
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        stream.write(imageBytes);
        BufferedImage originalImage = ImageIO.read(uploadedFile);
        BufferedImage resizeImage = resizeImage(originalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
        File newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);
        ImageIO.write(resizeImage, PNG.getExtension(), newFileJPG);
        stream.flush();
        stream.close();
        return filename;
    }

    public String saveFileForWishlist(String username, ServletContext context, String file) throws IOException {
        String base64Image = file.split(COMMA)[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

        if (imageBytes.length > MAX_UPLOAD_FILE_SIZE_IN_MB) {
            return null;
        }
        String filename = generateFileName() + POINT + PNG.getExtension();
        File dir = new File(context.getRealPath(PATH_IMAGE_USERS + username + SEPARATOR + "wishlist"));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File uploadedFile = new File(dir + SEPARATOR + filename);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        stream.write(imageBytes);
        BufferedImage originalImage = ImageIO.read(uploadedFile);
        BufferedImage resizeImage = resizeImage(originalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
        File newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);
        ImageIO.write(resizeImage, PNG.getExtension(), newFileJPG);
        stream.flush();
        stream.close();
        return filename;
    }

    public Map<String, String> saveMusicFile(String username, ServletContext context, MultipartFile file) throws IOException {
        Map<String, String> filenameMap = new HashMap<>();
        String filename = generateFileName() + POINT + MP3.getExtension();
        File dir = new File(context.getRealPath(PATH_MUSIC_USERS + username));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        byte[] bytes = file.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(dir + SEPARATOR + filename)));
        stream.write(bytes);
        stream.close();
        filenameMap.put(filename, MP3.getExtension());
        return filenameMap;
    }

    public Map<String, String> saveMusicFile(String username, ServletContext context, byte[] file) throws IOException {
        Map<String, String> filenameMap = new HashMap<>();
        String filename = generateFileName() + POINT + MP3.getExtension();
        File dir = new File(context.getRealPath(PATH_MUSIC_USERS + username));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(dir + SEPARATOR + filename)));
        stream.write(file);
        stream.close();
        filenameMap.put(filename, MP3.getExtension());
        return filenameMap;
    }

    public String saveFileInDialog(String username, ServletContext context, String file) throws IOException {
        if (file.isEmpty()) {
            return "1";
        }

        String base64Image = file.split(COMMA)[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

        if (imageBytes.length > MAX_UPLOAD_FILE_SIZE_IN_MB) {
            return "0";
        }
        String filename = generateFileName() + POINT + PNG.getExtension();
        File dir = new File(context.getRealPath(PATH_IMAGE_USERS + username + "/dialogs")); // TODO PATH_VIDEO_USERS вынести в аргументы функции
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File uploadedFile = new File(dir + SEPARATOR + filename); // TODO PATH_VIDEO_USERS вынести в аргументы функции
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        stream.write(imageBytes);
        BufferedImage originalImage = ImageIO.read(uploadedFile);
        BufferedImage resizeImage = resizeImage(originalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
        File newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);
        ImageIO.write(resizeImage, PNG.getExtension(), newFileJPG);
        stream.flush();
        stream.close();
        return filename;
    }

    public File saveS3File(String file) throws IOException {
        String base64Image = file.split(COMMA)[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
        String filename = generateFileName() + POINT + PNG.getExtension();

        File uploadedFile = new File(filename);
        if (imageBytes.length > MAX_UPLOAD_FILE_SIZE_IN_MB) {
            uploadedFile.delete();
            return uploadedFile;
        }
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        stream.write(imageBytes);
        BufferedImage originalImage = ImageIO.read(uploadedFile);
        BufferedImage resizeImage = resizeImage(originalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
        File newFileJPG = new File(filename);
        ImageIO.write(resizeImage, PNG.getExtension(), newFileJPG);
        stream.flush();
        stream.close();
        return newFileJPG;
    }
}