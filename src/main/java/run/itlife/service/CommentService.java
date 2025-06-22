package run.itlife.service;

import run.itlife.dto.CommentDto;
import run.itlife.entity.Comment;

import java.util.List;

//Интерфейс, отвечающий за логику создания комментариев
public interface CommentService {
    void createComment(CommentDto comment);
    void deleteComment(long id);
    List<Comment> findSortedCommentsByDate(long id);
}