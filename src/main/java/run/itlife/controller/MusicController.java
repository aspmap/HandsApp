package run.itlife.controller;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.itlife.dto.MusicDto;
import run.itlife.entity.User;
import run.itlife.service.MusicService;
import run.itlife.service.PlaylistService;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.SaveFile;

import javax.servlet.ServletContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import static run.itlife.enums.FileExtensions.MP3;
import static run.itlife.utils.OtherUtils.generateFileName;
import static run.itlife.utils.Properties.Files.*;
import static run.itlife.utils.Properties.Paths.*;
import static run.itlife.utils.Properties.ErrorMessages.*;

@Controller
public class MusicController {
    private final MusicService musicService;
    private final UserService userService;
    private final PlaylistService playlistService;
    private final ServletContext context;
    private static byte[] musicFile;
    @Autowired
    CommonsParams commonsParams;
    private static final Logger log = LoggerFactory.getLogger(MusicController.class);

    @Autowired
    public MusicController(MusicService musicService, UserService userService, PlaylistService playlistService, ServletContext context) {
        this.musicService = musicService;
        this.userService = userService;
        this.playlistService = playlistService;
        this.context = context;
    }

    @GetMapping("/music")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findMusic(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("musicAll", musicService.findAllByUser(user));
        return "media/music";
    }

    @GetMapping("/music/addfile")
    @PreAuthorize("hasRole('USER')")
    public String addMusic(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        return "media/add-file";
    }

    @PostMapping("/music/addinfo")
    @PreAuthorize("hasRole('USER')")
    public String confirmMusic(MusicDto musicDto, ModelMap modelMap, @RequestParam("file") MultipartFile file) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        commonsParams.setCommonParams(modelMap);
        if (!file.isEmpty()) {
            musicFile = file.getBytes();
            final String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String filename = generateFileName() + POINT + MP3.getExtension();
            File dir = new File(context.getRealPath(PATH_MUSIC_USERS + username + "/temp"));
            if (!dir.exists()) {
                dir.mkdirs();
            }
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(dir + SEPARATOR + filename)));
            stream.write(bytes);
            stream.close();
            File tempfile = new File(dir + SEPARATOR + filename);
            MP3File mp3file = (MP3File) AudioFileIO.read(tempfile);
            ID3v1Tag tagv1 = mp3file.getID3v1Tag();
            if (tagv1 != null) {
                musicDto.setProjectName(tagv1.getArtist().get(0).toString());
                musicDto.setSongName(tagv1.getFirstTitle());
                musicDto.setSongYear(tagv1.getFirstYear());
                modelMap.put("projectName", musicDto.getProjectName());
                modelMap.put("songName", musicDto.getSongName());
                modelMap.put("songYear", musicDto.getSongYear());
            }
            if (tempfile.exists()) {
                tempfile.delete();
            }
            return "media/add-info";
        } else {
            log.error(ERROR);
            commonsParams.setCommonParams(modelMap);
            return "messages-templates/errorAddMusicFile";
        }
    }

    @PostMapping("/music/save")
    @PreAuthorize("hasRole('USER')")
    public String saveMusic(MusicDto musicDto, ModelMap modelMap) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (musicFile.length > MAX_UPLOAD_MUSIC_FILE_SIZE_IN_MB) {
            commonsParams.setCommonParams(modelMap);
            return "messages-templates" + SEPARATOR + "errorFileSizeMusic";
        }
        Long musicId;
        SaveFile sf = new SaveFile();

        if (musicFile.length > 0) {
            try {
                Map<String, String> filenameMap = sf.saveMusicFile(username, context, musicFile);
                if (filenameMap == null) {
                    commonsParams.setCommonParams(modelMap);
                    return "messages-templates" + SEPARATOR + "errorFileSizeMusic";
                }
                for (Map.Entry<String, String> entry : filenameMap.entrySet()) {
                    musicDto.setFileName(entry.getKey());
                }
                musicId = musicService.createSong(musicDto);
                return "redirect:" + SEPARATOR + "music";
            } catch (Exception e) {
                log.error(ERROR + e);
                commonsParams.setCommonParams(modelMap);
                return "messages-templates" + SEPARATOR + "errorFileSizeMusic";
            }
        } else {
            log.error(ERROR);
            commonsParams.setCommonParams(modelMap);
            return "messages-templates" + SEPARATOR + "errorFileSizeMusic";
        }
    }

    @PostMapping("/music/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMusic(@PathVariable Long id) {
        musicService.deleteMusic(id);
    }

    @PostMapping("/music/results")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public String findPlaylists(ModelMap modelMap) {
        commonsParams.setCommonParams(modelMap);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        modelMap.put("findPlaylists", playlistService.findPlaylistsByUserId(user.getUserId()));
        return "media/results";
    }
}