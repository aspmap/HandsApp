package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.dto.MusicDto;
import run.itlife.entity.Music;
import run.itlife.entity.User;
import run.itlife.repository.MusicRepository;
import run.itlife.repository.UserRepository;

import java.util.ArrayList;

@Service
@Transactional
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    @Autowired
    public MusicServiceImpl(MusicRepository musicRepository, UserRepository userRepository) {
        this.musicRepository = musicRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<Music> findAllByUser(User user) {
        return musicRepository.findAllByUser(user);
    }

    @Override
    public Long createSong(MusicDto musicDto) {
        Music music = new Music();
        music.setFileName(musicDto.getFileName());
        music.setProjectName(musicDto.getProjectName());
        music.setSongName(musicDto.getSongName());
        music.setSongYear(musicDto.getSongYear());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        music.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        musicRepository.save(music);
        return music.getMusicId();
    }
}