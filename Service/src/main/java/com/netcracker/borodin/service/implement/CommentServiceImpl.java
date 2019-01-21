package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.converter.CommentMapper;
import com.netcracker.borodin.dto.CommentDTO;
import com.netcracker.borodin.dto.CommentFormDTO;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Comment;
import com.netcracker.borodin.entity.User;
import com.netcracker.borodin.exception.NotYourCommentException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.BookRepository;
import com.netcracker.borodin.repository.CommentRepository;
import com.netcracker.borodin.repository.UserRepository;
import com.netcracker.borodin.service.interfaces.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

  private CommentRepository commentRepository;
  private BookRepository bookRepository;
  private UserRepository userRepository;

  private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

  @Autowired
  public CommentServiceImpl(
      CommentRepository commentRepository,
      BookRepository bookRepository,
      UserRepository userRepository) {
    this.commentRepository = commentRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<CommentDTO> findAllComments() {
    log.debug("Start retrieving all comments from DB");
    return commentMapper.commentListToCommentDTOList(commentRepository.findAll());
  }

  @Override
  public CommentDTO addComment(CommentFormDTO commentForm) {
    log.debug("Start adding comment {}", commentForm);
    Optional<Book> book = bookRepository.findById(commentForm.getBookId());
    if (!book.isPresent()) {
      log.debug("Book with needed id {} not found", commentForm.getBookId());
      throw new ResourceNotFoundException(
          "We can`t add comment for nonexistent book with id " + commentForm.getBookId());
    } else {
      Optional<User> user = userRepository.findById(commentForm.getUserId());
      if (!user.isPresent()) {
        log.debug("User with needed id {} not found", commentForm.getUserId());
        throw new ResourceNotFoundException(
            "We can`t add comment from nonexistent user with id " + commentForm.getUserId());
      } else {
        Comment comment =
            Comment.builder()
                .book(book.get())
                .user(user.get())
                .createDate(new Date())
                .modifyDate(new Date())
                .text(commentForm.getText())
                .build();
        log.debug("Add comment {}", comment);
        return commentMapper.commentToCommentDTO(commentRepository.save(comment));
      }
    }
  }

  @Override
  public boolean updateComment(long commentId, String text, Long userId) {
    log.debug("Start updating comment");
    Optional<Comment> findComment = commentRepository.findById(commentId);
    if (findComment.isPresent()) {
      log.debug("Work with comment {}", findComment.get());
      if (findComment.get().getUser().getId().equals(userId)) {
        log.debug("Updating comment with id {}", commentId);
        commentRepository.update(new Date(), text, commentId);
        return true;
      } else {
        throw new NotYourCommentException(
            "Comment with this id " + commentId + " not your comment, you can` update it");
      }
    } else {
      log.debug("Comment with this id {} doesn`t exist", commentId);
      throw new ResourceNotFoundException("Comment with this id " + commentId + " not found");
    }
  }

  @Override
  public boolean deleteComment(long commentId, long userId) {
    log.debug("Start deleting comment with id {}", commentId);
    Optional<User> findsUser = userRepository.findById(userId);
    if (findsUser.isPresent()) {
      log.debug("Find user {}", findsUser.get());
      Optional<Comment> findComment = commentRepository.findByIdAndUser(commentId, findsUser.get());
      if (findComment.isPresent()) {
        log.debug("Delete comment {}", findComment.get());
        commentRepository.removeCommentById(findComment.get().getId());
        return true;
      } else {
        log.debug("Comment with id {} by user {} not found", commentId, findsUser.get());
        throw new ResourceNotFoundException(
            "We can`t find comment with id " + commentId + " by user with id " + userId);
      }
    } else {
      log.debug("User with id {} doesn`t exist", userId);
      throw new ResourceNotFoundException("We can`t find user with id " + userId);
    }
  }

  @Override
  public List<CommentDTO> showCommentByBookId(long bookId) {
    log.debug("Start retrieving comment for book with id {}", bookId);
    Optional<Book> findBook = bookRepository.findById(bookId);
    if (findBook.isPresent()) {
      log.debug("Needed book {}", findBook.get());
      return commentMapper.commentListToCommentDTOList(
          commentRepository.getAllByBook(findBook.get()));
    } else {
      throw new ResourceNotFoundException("We cant find book with id " + bookId);
    }
    //    return findBook
    //        .map(
    //            book ->
    // commentMapper.commentListToCommentDTOList(commentRepository.getAllByBook(book)))
    //        .orElseThrow(() -> new ResourceNotFoundException("Book with id " + bookId + " not
    // found"));
  }

  @Override
  public List<CommentDTO> showMyComment(long userId) {
    Optional<User> findUser = userRepository.findById(userId);
    if (findUser.isPresent()) {
      log.debug("Needed user {}", findUser.get());
      return commentMapper.commentListToCommentDTOList(
          commentRepository.getAllByUser(findUser.get()));
    } else {
      throw new ResourceNotFoundException("We cant find user with id " + userId);
    }
    //    return findUser
    //        .map(
    //            user ->
    // commentMapper.commentListToCommentDTOList(commentRepository.getAllByUser(user)))
    //        .orElseThrow(
    //            () -> new ResourceNotFoundException("User with this id " + userId + " not
    // found"));
  }
}
