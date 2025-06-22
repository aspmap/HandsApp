package run.itlife.utils.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import static run.itlife.utils.Properties.ErrorMessages.*;
import static run.itlife.utils.Properties.Paths.*;

@Service
public class InformationGatheringMedia implements InformationGathering {
    private Logger log = LoggerFactory.getLogger(InformationGatheringMedia.class);

    @Override
    public void copyUserInfo(String username, File dirOrigin, File dirDestination) {
        ArrayList<File> files = new ArrayList<>();
        if (dirOrigin.isDirectory()) {
            for (File d : dirOrigin.listFiles()) {
                files.add(d);
            }
        }
        if (!dirDestination.exists()) {
            dirDestination.mkdirs();
        }
        AtomicInteger ai = new AtomicInteger();
        for (ai.get(); ai.get() < files.size(); ai.incrementAndGet()) {
            Path sourcePath = Paths.get(files.get(ai.get()).getPath());
            Path destPath = Paths.get(dirDestination + SEPARATOR + files.get(ai.get()).getName());
            try {
                Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                log.error(ERROR + e);
            }
        }
    }
}
