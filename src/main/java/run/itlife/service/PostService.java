package run.itlife.service;

import run.itlife.dto.PostDto;
import run.itlife.entity.Post;

import java.util.List;

//Интерфейс, отвечающий за логику создания постов, валидацию, изменение и т.д.
public interface PostService {
    List<Post> findAllPosts();
    List<PostDto> findAllPostsAsDto();
    List<PostDto> findDtos(String search);
    List<Post> search(String search);
    long createPost(PostDto postDto);
    List<Post> findByUser(String username);
    void checkAuthority(long postId);
    PostDto getAsDto(long postId);
    void update(PostDto postDto);
    Post findById(long id);
    void delete(long id);
    List<Post> findByUserName(String username);
    int countPosts(String username);
    Long countComments(Long id);
    List<Post> findSortedPostsByDate(String username);
    List<Post> findSubscribesPosts(String username);
    int countSubscribesPosts(String username);
    List<Post> findTags(String substring);
    int countSearchTags(String substring);
    List<Long> isLikePost(String username);
    List<Post> findMyLikesPosts(String username);
    Long countMyLikesPosts(String username);
    boolean isClosedProfile(String username);
    boolean isClosedProfileByPostId(long id);
}