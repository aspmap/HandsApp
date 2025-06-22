package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.PlaylistMusic;

import javax.transaction.Transactional;

public interface PlaylistMusicRepository extends JpaRepository<PlaylistMusic, Long> {
    @Query(value = "select distinct p.playlist_name from playlist p " +
            "where p.playlist_id = ? ", nativeQuery = true)
    String findNamePlaylist(Long playlistId);

    @Modifying
    @Transactional
    @Query(value = "delete from playlist_music pm " +
            "where pm.playlist_id = ? and pm.music_id = ? ", nativeQuery = true)
    void deleteMusicFromPlaylist(Long playlistId, Long musicId);
}
