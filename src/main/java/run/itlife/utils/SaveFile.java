package run.itlife.utils;

import org.springframework.web.multipart.MultipartFile;
import run.itlife.service.PostService;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static run.itlife.enums.FileExtensions.*;
import static run.itlife.enums.FileExtensions.PNG;
import static run.itlife.utils.EditImage.resizeImage;
import static run.itlife.utils.OtherUtils.generateFileName;

public class SaveFile {

    private static final String PATH_VIDEO_USERS = "/resources/video/users/";
    private static final String PATH_IMAGE_USERS = "/resources/img/users/";
    private static final String SEPARATOR = "/";
    private static final String COMMA = ",";
    private static final String POINT = ".";
    private static final int IMAGE_WIDTH = 500;
    private static final int IMAGE_HEIGHT = 500;

    public Map<String, String> saveFile(String username, ServletContext context, MultipartFile file) throws IOException {

        Map<String, String> filenameMap = new HashMap<>();
        String extension;

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

    public String saveFile(String username, ServletContext context, String file) throws IOException {

        String base64Image = file.split(COMMA)[1];
        byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);

        String filename = generateFileName() + POINT + PNG.getExtension();
        File dir = new File(context.getRealPath(PATH_IMAGE_USERS + username)); // TODO PATH_VIDEO_USERS вынести в аргументы функции
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File uploadedFile = new File(dir + SEPARATOR + filename); // TODO PATH_VIDEO_USERS вынести в аргументы функции
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
        stream.write(imageBytes);
        BufferedImage originalImage = ImageIO.read(uploadedFile);
        BufferedImage resizeImage = null;
        File newFileJPG = null;
        resizeImage = resizeImage(originalImage, IMAGE_WIDTH, IMAGE_HEIGHT);
        newFileJPG = new File(dir.getAbsolutePath() + File.separator + filename);
        ImageIO.write(resizeImage, PNG.getExtension(), newFileJPG);

        stream.flush();
        stream.close();
        return filename;
    }
}