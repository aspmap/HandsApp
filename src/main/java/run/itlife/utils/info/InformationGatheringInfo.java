package run.itlife.utils.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import run.itlife.entity.Post;
import run.itlife.entity.User;
import run.itlife.service.PostService;
import run.itlife.service.UserService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static run.itlife.enums.FileExtensions.TXT;
import static run.itlife.messages.ErrorMessages.ERROR;
import static run.itlife.utils.OtherUtils.generateFileName;
import static run.itlife.utils.SaveFile.POINT;

@Service
public class InformationGatheringInfo implements InformationGathering {
    private final UserService userService;
    private final PostService postService;
    private Logger log = LoggerFactory.getLogger(InformationGatheringInfo.class);

    public InformationGatheringInfo(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public void copyUserInfo(String username, File dirOrigin, File dirDestination) {
        User user = userService.findByUsername(username);
        String filenameUser = "user_" + generateFileName() + POINT + TXT.getExtension();

        if (!dirDestination.exists()) {
            dirDestination.mkdirs();
        }
        File txtFileUser = new File(dirDestination, filenameUser);
        try(Writer wr = new FileWriter(txtFileUser, Charset.forName("cp1251"));) {
            wr.write("ID: " + user.getUserId() + "\n");
            wr.write("Username: " + user.getUsername() + "\n");
            wr.write("Firstname: " + user.getFirstname() + "\n");
            wr.write("Surname: " + user.getSurname() + "\n");
            wr.write("E-Mail: " + user.getEmail() + "\n");
            wr.write("Photo: " + user.getPhoto() + "\n");
            wr.write("Info: " + user.getInfo() + "\n");
            wr.write("Phone: " + user.getPhone() + "\n");
            wr.write("Sex: " + user.getSex() + "\n");
            wr.write("Site: " + user.getWww() + "\n");
            wr.write("Last visit: " + user.getLastVisit() + "\n");
            wr.write("Is active: " + user.getIsActive() + "\n");
        } catch (IOException e) {
            log.error(ERROR + e);
        }

        List<Post> posts = postService.findByUserName(username);
        String filenamePosts = "posts_" + generateFileName() + POINT + TXT.getExtension();
        if (!dirDestination.exists()) {
            dirDestination.mkdirs();
        }
        File txtFilePosts = new File(dirDestination, filenamePosts);
        AtomicInteger ai = new AtomicInteger();
        try(Writer wrPosts = new FileWriter(txtFilePosts, Charset.forName("cp1251"));) {
            for (ai.get(); ai.get() < posts.size(); ai.incrementAndGet()) {
                wrPosts.write("Post ID: " + posts.get(ai.get()).getPostId() + "\n");
                wrPosts.write("Content: " + posts.get(ai.get()).getContent() + "\n");
                wrPosts.write("Photo: " + posts.get(ai.get()).getPhoto() + "\n");
                wrPosts.write("User: " + posts.get(ai.get()).getUser().getUsername() + "\n");
                wrPosts.write("Content: " + posts.get(ai.get()).getCreatedAt() + "\n");
                wrPosts.write("--------------------------------\n");
            }
        } catch (IOException e) {
            log.error(ERROR + e);
        }
    }
}
