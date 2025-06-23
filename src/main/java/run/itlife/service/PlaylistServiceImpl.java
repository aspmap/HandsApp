package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.dto.PlaylistDto;
import run.itlife.entity.Playlist;
import run.itlife.repository.PlaylistRepository;
import run.itlife.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;

import static run.itlife.utils.SecurityUtils.*;

@Service
@Transactional
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    @Autowired
    public PlaylistServiceImpl(PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ArrayList<Playlist> findAllPlaylistsByUserId(Long userId) {
        return playlistRepository.findAllPlaylistsByUserId(userId);
    }

    @Override
    public Long createPlaylist(PlaylistDto playlistDto) {
        Playlist playlist = new Playlist();
        playlist.setPlaylistName(playlistDto.getPlaylistName());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        playlist.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        playlistRepository.save(playlist);
        return playlist.getPlaylistId();
    }

    @Override
    public void deletePlaylist(Long id) {
        String username = playlistRepository.findById(id)
                .orElseThrow()
                .getUser().getUsername();
        if (!hasAuthority(username) && !hasRole("ADMIN")) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        playlistRepository.deleteById(id);
    }

    @Override
    public ArrayList<Playlist> findPlaylistsByUserId(Long userId) {
        return playlistRepository.findPlaylistsByUserId(userId);
    }

    @Override
    public Long findPlaylistId(String substring) {
        return playlistRepository.findPlaylistId(substring);
    }
}
