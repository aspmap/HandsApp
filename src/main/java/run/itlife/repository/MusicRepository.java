package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Music;
import run.itlife.entity.User;

import java.util.ArrayList;

public interface MusicRepository extends JpaRepository<Music, Long> {
    ArrayList<Music> findAllByUser(User user);

    @Query(value = "select m.user_id, m.file_name, m.music_id, m.project_name, m.song_name, m.song_year " +
            "from music m " +
            "left join playlist_music pm on pm.music_id = m.music_id " +
            "left join playlist p on pm.playlist_id = p.playlist_id " +
            "where pm.playlist_id = ? ", nativeQuery = true)
    ArrayList<Music> getPlaylistByPlaylistId(Long playlistId);
}