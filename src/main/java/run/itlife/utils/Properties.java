package run.itlife.utils;

public class Properties {

    public interface Files {
        int MAX_UPLOAD_FILE_SIZE_IN_MB = 20 * 1024 * 1024; // 20 МБ
        int MAX_UPLOAD_MUSIC_FILE_SIZE_IN_MB = 100 * 1024 * 1024; // 100 МБ
        int MAX_UPLOAD_VIDEO_FILE_SIZE_IN_MB = 100 * 1024 * 1024; // 100 МБ
        int IMAGE_WIDTH = 500;
        int IMAGE_HEIGHT = 500;
    }

    public interface Paths {
        String PATH_VIDEO_USERS = "/resources/video/users/";
        String PATH_IMAGE_USERS = "/resources/img/users/";
        String PATH_MUSIC_USERS = "/resources/music/users/";
        String PATH_FILES = "/resources/users_archive/users/";
        String SEPARATOR = "/";
        String COMMA = ",";
        String POINT = ".";
    }

    public interface ErrorMessages {
        public static String ERROR = "Error: ";
        public static String WARNING = "Warning: ";
        public static String INFO = "Info: ";
        public static String DEBUG = "Debug: ";
        public static String NOT_PUBLISH_POST = "Publishing error. The file does not match the format, or the file name is too long, or the file is not attached";
    }
}