package run.itlife.service;

import run.itlife.dto.PostDto;
import run.itlife.entity.Likes;
import run.itlife.entity.Post;
import java.util.List;

//Интерфейс, отвечающий за логику создания постов, валидацию, изменение и т.д.
public interface PostService {

    List<Post> listAllPosts();
    List<PostDto> listAllPostsAsDto();
    List<PostDto> searchDtos(String search);
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
    List<Post> sortedPostsByDate(String username);
    List<Post> findSubscribesPosts(String username);
    int countSubscribesPosts(String username);
    List<Post> searchTags(String substring);
    int countSearchTags(String substring);
    List<Long> isLikePost(String username);
    List<Post> selectMyLikesPosts(String username);
    Long countMyLikesPosts(String username);

}