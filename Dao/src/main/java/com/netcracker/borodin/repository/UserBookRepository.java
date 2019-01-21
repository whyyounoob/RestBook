package com.netcracker.borodin.repository;

import com.netcracker.borodin.SQLConstant;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.User;
import com.netcracker.borodin.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {
  List<UserBook> getAllByUser(User user);

  Optional<UserBook> findByUserAndBook(User user, Book book);

  @Query(value = SQLConstant.GET_AVERAGE_RATE, nativeQuery = true)
  Optional<BigDecimal> getAverageRate(Long id);

  @Modifying
  @Query(value = SQLConstant.UPDATE_USER_BOOK, nativeQuery = true)
  void updateMyBook(long userId, long bookId, int rate);
}
