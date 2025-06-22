package run.itlife.service;

public interface LikesService {
    int countLikesByPostId(long postId);
    int countLikesByUsername(String username);
    int isLikePostForCurrentUser(long postId, String currentUsername);
    void createLike(Long postId);
    void deleteLike(long userId, long postId);
}
