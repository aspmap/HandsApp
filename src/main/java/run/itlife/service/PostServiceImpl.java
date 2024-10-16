package run.itlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import run.itlife.dto.CommentDto;
import run.itlife.dto.PostDto;
import run.itlife.entity.Comment;
import run.itlife.entity.Likes;
import run.itlife.entity.Post;
import run.itlife.repository.PostRepository;
import run.itlife.repository.UserRepository;
import run.itlife.utils.SecurityUtils;
import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static run.itlife.utils.SecurityUtils.*;

// Уровень обслуживания
// Класс, реализующий интерфейс, который отвечает за логику создания постов, валидацию, изменение и т.д.
@Service
@Transactional
public class PostServiceImpl implements PostService {

    // сервисы в свою очередь включают репозиторий
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> listAllPosts() {
        List<Post> posts =  postRepository.findAll(Sort.by("createdAt").descending());
        for (Post p : posts) {
            p.getComments().size();
        }
        return posts;
    }

    @Override
    public List<PostDto> listAllPostsAsDto() {
        return toDtoList(listAllPosts());
    }

    @Override
    public List<Post> search(String search) {
        return postRepository.findByContentLikeIgnoreCase("%" + search +"%");
    }

    @Override
    public List<PostDto> searchDtos(String search) {
        return toDtoList(search(search));
    }

    private List<PostDto> toDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findByUser(String username) {
        List<Post> posts = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username))
                .getPosts();
        posts.size();
        return posts;
    }

    @Override
    public long createPost(PostDto postDto) {
        Post post = new Post();
        post.setPhoto(postDto.getPhoto());
        post.setExtFile(postDto.getExtFile());
        post.setStorageType(postDto.getStorageType());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //String username = SecurityUtils.getCurrentUserDetails().getUsername();
        post.setUser(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username)));
        postRepository.save(post);
        return post.getPostId();
    }

    @Override
    public void checkAuthority(long postId) {
        SecurityUtils.checkAuthority(postRepository.findById(postId)
                .orElseThrow()
                .getUser().getUsername());
    }

    @Override
    public PostDto getAsDto(long postId) {
        return toDto(postRepository.findById(postId).orElseThrow());
    }

    @Override
    public void update(PostDto postDto) {
        checkAuthority(postDto.getPostId());
        Post post = postRepository.findById(postDto.getPostId()).orElseThrow();
        if (!StringUtils.isEmpty(postDto.getPhoto()))
            post.setPhoto(postDto.getPhoto());
        if (!StringUtils.isEmpty(postDto.getContent()))
            post.setContent(postDto.getContent());
        if (!StringUtils.isEmpty(postDto.getExtFile()))
            post.setExtFile(postDto.getExtFile());
        postRepository.save(post);
    }

    @Override
    public Post findById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.getComments().size();
        return post;
    }

    @Override
    public void delete(long id) {
        String username = postRepository.findById(id)
                .orElseThrow()
                .getUser().getUsername();
        if (!hasAuthority(username) && !hasRole("ADMIN")) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> findByUserName(String username) {
        List<Post> posts = postRepository.findByUserName(username);
        for (Post p : posts) {
            p.getComments().size();
        }
        return posts;
    }

    @Override
    public int countPosts(String username) {
        int count = postRepository.countPosts(username);
        return count;
    }

    @Override
    public Long countComments(Long id) {
        Long countComments = postRepository.countComments(id);
        if(countComments == null) countComments = 0L;
        return countComments;
    }

    @Override
    public List<Post> sortedPostsByDate(String username) {
        List<Post> posts = postRepository.sortedPostsByDate(username);
        for (Post p : posts) {
            p.getComments().size();
        }
        return posts;
    }

    private PostDto toDto(Post post) {
        PostDto dto = new PostDto();
        dto.setPostId(post.getPostId());
        dto.setPhoto(post.getPhoto());
        dto.setContent(post.getContent());
        dto.setExtFile(post.getExtFile());
        //для REST
        dto.setComments(post.getComments().stream()
                .map(this::commentToDto)
                .collect(Collectors.toList()));
        dto.setUsername(post.getUser().getUsername());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        /////
        return dto;
    }

    //для REST
    private CommentDto commentToDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setCommentText(comment.getCommentText());
        dto.setUsername(comment.getUser().getUsername());
        dto.setCommentId(comment.getCommentId());
        dto.setPostId(comment.getPost().getPostId());
        return dto;
    }

    @Override
    public List<Post> findSubscribesPosts(String username) {
        List<Post> posts = postRepository.findSubscribesPosts(username);
        for (Post p : posts) {
            p.getComments().size();
        }
        return posts;
    }

    @Override
    public int countSubscribesPosts(String username) {
        int count = postRepository.countSubscribesPosts(username);
        return count;
    }

    @Override
    public List<Post> searchTags(String substring) {
        List<Post> posts = postRepository.searchTags("%#" + substring +"%");
        return posts;
    }

    @Override
    public int countSearchTags(String substring) {
        return postRepository.countSearchTags("%#" + substring +"%");
    }

    @Override
    public List<Long> isLikePost(String username) {
        List<Long> isLike = postRepository.isLikePost(username);
        return isLike;
    }

    @Override
    public List<Post> selectMyLikesPosts(String username) {
        return postRepository.selectMyLikesPosts(username);
    }

    @Override
    public Long countMyLikesPosts(String username) {
        return postRepository.countMyLikesPosts(username);
    }

}