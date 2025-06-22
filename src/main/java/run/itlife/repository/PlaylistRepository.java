package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import run.itlife.entity.Playlist;
import run.itlife.entity.User;

import java.util.ArrayList;
import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query(value = "select * from playlist " +
            "where user_id = ? ", nativeQuery = true)
    ArrayList<Playlist> getAllPlaylistsByUserId(Long userId);

    @Query(value = "select * from playlist " +
            "where upper(playlist_name) LIKE upper(?) and user_id = ? limit 5 ", nativeQuery = true)
    ArrayList<Playlist> searchPlaylists(String substring, Long userId);

    @Query(value = "select * from playlist " +
            "where user_id = ? ", nativeQuery = true)
    ArrayList<Playlist> searchPlaylistsByUserId(Long userId);

    @Query(value = " select playlist_id from playlist " +
            "where playlist_name LIKE ? ", nativeQuery = true)
    Long getPlaylistId(String substring);

}
