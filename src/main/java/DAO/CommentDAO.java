package DAO;

import java.util.List;
import bean.Comment;

public interface CommentDAO {
    void insert(Comment comment);
    void update(Comment comment);
    void delete(int id);
    Comment findById(int id);
    List<Comment> findAll();
    List<Comment> findByVideoId(String videoId);
    List<Comment> findUserId(String userId);
}
