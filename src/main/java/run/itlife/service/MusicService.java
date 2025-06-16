package run.itlife.service;

import org.springframework.web.bind.annotation.PathVariable;
import run.itlife.dto.MusicDto;
import run.itlife.entity.Music;
import run.itlife.entity.Playlist;
import run.itlife.entity.User;

import java.util.ArrayList;

public interface MusicService {
    ArrayList<Music> findAllByUser(User user);
    Long createSong(MusicDto musicDto);
    void deleteMusic(@PathVariable Long id);

}