package com.netcracker.borodin.service.interfaces;

import com.netcracker.borodin.dto.CommentDTO;
import com.netcracker.borodin.dto.CommentFormDTO;
import com.netcracker.borodin.entity.Comment;

import java.util.List;

/**
 * Service for work with {@link Comment}
 *
 * @author Maxim Borodin
 */
public interface CommentService {

  /**
   * This method for getting all comments.
   *
   * @return list of comments
   */
  List<CommentDTO> findAllComments();

  /**
   * This method for adding new comment
   *
   * @param commentForm - info for the new comment
   * @return new comment
   */
  CommentDTO addComment(CommentFormDTO commentForm);

  /**
   * This method updating comment by id and user id
   *
   * @param commentId - id comment
   * @param text - text of new comment
   * @param userId - id of the user which create comment
   * @return true if comment updating
   */
  boolean updateComment(long commentId, String text, Long userId);

  /**
   * This method for deleting comment
   *
   * @param commentId - id comment
   * @param userId - id of the user which create this comment
   * @return true if comment deleted
   */
  boolean deleteComment(long commentId, long userId);

  /**
   * This method for retrieving comment for special book
   *
   * @param bookId - id of the book
   * @return list of comments
   */
  List<CommentDTO> showCommentByBookId(long bookId);

  /**
   * This method for retrieving comments by current user
   *
   * @param userId - id user
   * @return list of comments
   */
  List<CommentDTO> showMyComment(long userId);
}
