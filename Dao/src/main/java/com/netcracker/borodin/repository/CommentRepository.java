package com.netcracker.borodin.repository;

import com.netcracker.borodin.SQLConstant;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Comment;
import com.netcracker.borodin.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> getAllByBook(Book book);

    List<Comment> getAllByUser(User user);

    @Query(value = SQLConstant.UPDATE_COMMENT,
            nativeQuery = true)
    void update(Date date, String text, long id);

    Optional<Comment> findByIdAndUser(long id, User user);

    void removeCommentById(long id);

}
