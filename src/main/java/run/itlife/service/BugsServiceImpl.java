package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.dto.BugsDto;
import run.itlife.entity.Bugs;
import run.itlife.entity.User;
import run.itlife.repository.BugsRepository;
import run.itlife.repository.UserRepository;
import run.itlife.utils.SecurityUtils;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BugsServiceImpl implements BugsService {

    private final BugsRepository bugsRepository;
    private final UserRepository userRepository;

    @Autowired
    public BugsServiceImpl(BugsRepository bugsRepository, UserRepository userRepository) {
        this.bugsRepository = bugsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void create(BugsDto bugsDto) {
        Bugs bugs = new Bugs();
        bugs.setBugText(bugsDto.getBugText());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //String username = SecurityUtils.getCurrentUserDetails().getUsername();
        bugs.setUserId(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        bugs.setCreatedAt(LocalDateTime.now());
        bugsRepository.save(bugs);
    }

    @Override
    public void createFromKafka(BugsDto bugsDto) {
        Bugs bugs = new Bugs();
        String username = bugsDto.getUsername();
        User userId = userRepository.findByUsername(username).orElseThrow();
        bugs.setUsername(bugsDto.getUsername());
        bugs.setBugText(bugsDto.getBugText());
        bugs.setUserId(userId);
        LocalDateTime localdatetime = LocalDateTime.parse(bugsDto.getCreatedAtText());
        bugs.setCreatedAt(localdatetime);
        bugsRepository.save(bugs);
    }

    @Override
    public List<Bugs> listAllBugs() {
        List<Bugs> bugs =  bugsRepository.findAll(Sort.by("createdAt").descending());
        for (Bugs b : bugs) {
            b.getBugText().length();
        }
        return bugs;
    }

}
