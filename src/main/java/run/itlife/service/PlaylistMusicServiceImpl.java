package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.dto.PlaylistMusicDto;
import run.itlife.entity.Music;
import run.itlife.entity.PlaylistMusic;
import run.itlife.repository.MusicRepository;
import run.itlife.repository.PlaylistMusicRepository;

import java.util.ArrayList;

import static run.itlife.utils.SecurityUtils.*;

@Service
@Transactional
public class PlaylistMusicServiceImpl implements PlaylistMusicService {
    private final PlaylistMusicRepository playlistMusicRepository;
    private final MusicRepository musicRepository;

    @Autowired
    public PlaylistMusicServiceImpl(PlaylistMusicRepository playlistMusicRepository, MusicRepository musicRepository) {
        this.playlistMusicRepository = playlistMusicRepository;
        this.musicRepository = musicRepository;
    }

    @Override
    public ArrayList<Music> findPlaylistByPlaylistId(Long playlistId) {
        return musicRepository.findPlaylistByPlaylistId(playlistId);
    }

    @Override
    public String findNamePlaylist(Long playlistId) {
        return playlistMusicRepository.findNamePlaylist(playlistId);
    }

    @Override
    public void addToPlaylist(PlaylistMusicDto playlistMusicDto) {
        PlaylistMusic playlistMusic = new PlaylistMusic();
        playlistMusic.setMusicId(playlistMusicDto.getMusicId());
        playlistMusic.setPlaylistId(playlistMusicDto.getPlaylistId());
        playlistMusicRepository.save(playlistMusic);
    }

    @Override
    public void deleteMusicFromPlaylist(Long playlistId, Long musicId) {
        playlistMusicRepository.deleteMusicFromPlaylist(playlistId, musicId);
    }
}
