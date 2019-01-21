package com.netcracker.borodin.service;

import com.netcracker.borodin.converter.CommentConverter;
import com.netcracker.borodin.dto.CommentDTO;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Comment;
import com.netcracker.borodin.entity.User;
import com.netcracker.borodin.repository.BookRepository;
import com.netcracker.borodin.repository.CommentRepository;
import com.netcracker.borodin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    private CommentRepository commentRepository;

    private BookRepository bookRepository;

    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean addComment(String text, long bookId, long userId) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setCreateDate(new Date());
        comment.setModifyDate(new Date());
        Optional<Book> findBook = bookRepository.findById(bookId);
        if (findBook.isPresent()) {
            comment.setBook(findBook.get());
        } else return false;
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isPresent()) {
            comment.setUser(findUser.get());
        } else return false;
        commentRepository.save(comment);
        return true;
    }

    @Transactional
    public void updateComment(long commentId, String text) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isPresent()) {
            commentRepository.update(new Date(), text, commentId);
        }
    }

    @Transactional
    public boolean deleteComment(long commentId, long userId) {
        Optional<User> findsUser = userRepository.findById(userId);
        if (findsUser.isPresent()) {
            Optional<Comment> findComment = commentRepository.findByIdAndUser(commentId, findsUser.get());
            if (findComment.isPresent()) {
                commentRepository.removeCommentById(findComment.get().getId());
                return true;
            } else return false;
        } else return false;
    }

    public List<CommentDTO> showComment(long bookId) {
        List<CommentDTO> finds = new ArrayList<>();
        Optional<Book> findBook = bookRepository.findById(bookId);
        if (findBook.isPresent()) {
            for (Comment comment : commentRepository.getAllByBook(findBook.get())) {
                CommentDTO commentDTO = CommentConverter.convert(comment);
                finds.add(commentDTO);
            }
        }
        return finds;
    }

    public List<CommentDTO> showMyComment(long userId) {
        List<CommentDTO> finds = new ArrayList<>();
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isPresent()) {
            for (Comment comment : commentRepository.getAllByUser(findUser.get())) {
                CommentDTO commentDTO = CommentConverter.convert(comment);
                finds.add(commentDTO);
            }
        }
        return finds;
    }
}
