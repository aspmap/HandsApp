package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Music;
import run.itlife.entity.Playlist;
import run.itlife.entity.PlaylistMusic;

import javax.transaction.Transactional;
import java.util.ArrayList;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusic, Long> {
    @Query(value = "select distinct p.playlist_name from playlist p " +
            "where p.playlist_id = ? ", nativeQuery = true)
    String getNamePlaylist(Long playlistId);

    @Modifying
    @Transactional
    @Query(value = "delete from playlist_music pm " +
            "where pm.playlist_id = ? and pm.music_id = ? ", nativeQuery = true)
    void deleteFromPlaylist(Long playlistId, Long musicId);
}
