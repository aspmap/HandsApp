package run.itlife.enums;

public enum FileExtensions {

    MP4 ("mp4"),
    MOV ("mov"),
    JPG ("jpg"),
    PNG ("png");

    private String extension;

    FileExtensions(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
