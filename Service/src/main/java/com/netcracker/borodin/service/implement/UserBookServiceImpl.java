package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.converter.UserBookMapper;
import com.netcracker.borodin.dto.UserBookDTO;
import com.netcracker.borodin.dto.UserBookForm;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.User;
import com.netcracker.borodin.entity.UserBook;
import com.netcracker.borodin.entity.UserBookId;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.BookRepository;
import com.netcracker.borodin.repository.UserBookRepository;
import com.netcracker.borodin.repository.UserRepository;
import com.netcracker.borodin.service.interfaces.UserBookService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserBookServiceImpl implements UserBookService {

  private final UserRepository userRepository;

  private final UserBookRepository userBookRepository;

  private final BookRepository bookRepository;

  private UserBookMapper userBookMapper = Mappers.getMapper(UserBookMapper.class);

  @Autowired
  public UserBookServiceImpl(
      UserRepository userRepository,
      UserBookRepository userBookRepository,
      BookRepository bookRepository) {
    this.userRepository = userRepository;
    this.userBookRepository = userBookRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  public List<UserBookDTO> getMyBooks(long userId) {
    log.debug("Start retrieving books for user with id {}", userId);
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
      log.debug("This user {} exist", user.get());
      List<UserBook> list = userBookRepository.getAllByUser(user.get());
      return list.stream()
          .map(userBook -> userBookMapper.userBookToUserBookDTO(userBook))
          .collect(Collectors.toList());
    } else {
      throw new ResourceNotFoundException("User with id = " + userId + " does not exist");
    }
  }

  @Override
  public boolean addMyBook(UserBookForm userBookForm, long userId) {
    log.debug(
        "Start adding book with id {} to user`s list with id {}", userBookForm.getBookId(), userId);
    Optional<User> user = userRepository.findById(userId);
    if (!user.isPresent()) {
      log.debug("User with id {} not found", userId);
      throw new ResourceNotFoundException("User with id " + userId + " doesn`t exist");
    }
    log.debug("User with id {} was found", userId);
    Optional<Book> book = bookRepository.findById(userBookForm.getBookId());
    if (!book.isPresent()) {
      log.debug("Book with id {} not found", userBookForm.getBookId());
      throw new ResourceNotFoundException(
          "Book with id = " + userBookForm.getBookId() + " not found");
    }
    log.debug("Book with id {} was found", userBookForm.getBookId());
    Optional<UserBook> findUserBook = userBookRepository.findByUserAndBook(user.get(), book.get());
    if (findUserBook.isPresent()) {
      log.debug(
          "This book {} already exist in list of this user {} with rate {}",
          findUserBook.get().getBook(),
          findUserBook.get().getUser(),
          findUserBook.get().getRate());
      throw new ResourceAlreadyExistException(
          "This book "
              + findUserBook.get().getBook()
              + " already exist in list of this user "
              + findUserBook.get().getUser()
              + " with rate "
              + findUserBook.get().getRate());
    } else {
      log.debug("Book with id {} was found", userBookForm.getBookId());
      UserBook userBook =
          UserBook.builder()
              .user(user.get())
              .book(book.get())
              .rate(userBookForm.getRate())
              .id(UserBookId.builder().bookId(userBookForm.getBookId()).userId(userId).build())
              .build();
      userBookRepository.save(userBook);
      log.debug(
          "Book {} save with rate {} by user {}", book.get(), userBookForm.getRate(), user.get());
      Optional<BigDecimal> avgRate = userBookRepository.getAverageRate(userBookForm.getBookId());
      if (avgRate.isPresent()) {
        log.debug("Calculate average rate {}", avgRate.get());
        bookRepository.updateAverageRate(userBookForm.getBookId(), avgRate.get().doubleValue());
        return true;
      } else {
        log.debug("Something went wrong in calculating average rate, rollback");
        throw new ResourceNotFoundException(
            "Cant calculate average rate for book with id " + userBookForm.getBookId());
      }
    }
  }

  @Override
  public boolean updateRate(UserBookForm userBookForm, long userId) {
    log.debug(
        "Start updating rate for book with id {} by user with id {}",
        userBookForm.getBookId(),
        userId);
    Optional<User> user = userRepository.findById(userId);
    if (!user.isPresent()) {
      log.debug("User with id {} not found", userId);
      throw new ResourceNotFoundException("User with id " + userId + " not found");
    }
    log.debug("User with id {} was found", userId);
    Optional<Book> book = bookRepository.findById(userBookForm.getBookId());
    if (!book.isPresent()) {
      throw new ResourceNotFoundException(
          "Book with id " + userBookForm.getBookId() + " not found");
    }
    log.debug("Book with id {} was found", userBookForm.getBookId());
    Optional<UserBook> userBook = userBookRepository.findByUserAndBook(user.get(), book.get());
    if (userBook.isPresent()) {
      userBookRepository.updateMyBook(userId, userBookForm.getBookId(), userBookForm.getRate());
      log.debug(
          "Rate was updated in book {} by user {}, now it {}",
          book.get(),
          user.get(),
          userBookForm.getRate());
      Optional<BigDecimal> avgRate = userBookRepository.getAverageRate(userBookForm.getBookId());
      if (avgRate.isPresent()) {
        log.debug(
            "Calculate average rate for book {}, now it {}",
            book.get(),
            avgRate.get().doubleValue());
        bookRepository.updateAverageRate(userBookForm.getBookId(), avgRate.get().doubleValue());
        log.debug("Average rate updated");
        return true;
      } else {
        log.debug("Something went wrong in calculating average rate, rollback");
        throw new ResourceNotFoundException(
            "Cant calculate average rate for book with id " + userBookForm.getBookId());
      }
    } else {
      log.debug(
          "We cant find book with id {} in list of user with id {}",
          userBookForm.getBookId(),
          userId);
      throw new ResourceNotFoundException(
          "We cant find book with id "
              + userBookForm.getBookId()
              + " in list of user with id "
              + userId);
    }
  }
}
