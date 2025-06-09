package run.itlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import run.itlife.entity.Music;
import run.itlife.entity.User;

import java.util.ArrayList;

public interface MusicRepository extends JpaRepository<Music, Long> {
    ArrayList<Music> findAllByUser(User user);
}