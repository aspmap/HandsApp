package run.itlife.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import run.itlife.entity.Post;

public interface PostPaginationService {
    Page<Post> findSubscribesPosts(String username, Pageable pageable);
}