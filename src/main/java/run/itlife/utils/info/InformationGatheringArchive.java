package run.itlife.utils.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static run.itlife.messages.ErrorMessages.ERROR;

public class InformationGatheringArchive implements InformationGathering {
    private Logger log = LoggerFactory.getLogger(InformationGatheringArchive.class);
    @Override
    public void copyUserInfo(String username, File dirOrigin, File dirDestination)  {
        try (FileOutputStream fos = new FileOutputStream(dirOrigin.getAbsolutePath() + ".zip"); ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            zipFile(dirOrigin, dirOrigin.getName(), zipOut);
        } catch (IOException e) {
            log.error(ERROR + e);
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            }
            zipOut.closeEntry();
            var children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try (var fis = new FileInputStream(fileToZip)) {
            var zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            var bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }
}
