package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.itlife.entity.Post;
import run.itlife.repository.PostPaginationRepository;

@Service
@Transactional
public class PostPaginationServiceImpl implements PostPaginationService {
    private final PostPaginationRepository postPaginationRepository;

    @Autowired
    public PostPaginationServiceImpl(PostPaginationRepository postPaginationRepository) {
        this.postPaginationRepository = postPaginationRepository;
    }

    @Override
    public Page<Post> findSubscribesPosts(String username, Pageable pageable) {
        Page<Post> posts = postPaginationRepository.findSubscribesPosts(username, pageable);
        for (Post p : posts) {
            p.getComments().size();
        }
        return posts;
    }
}
