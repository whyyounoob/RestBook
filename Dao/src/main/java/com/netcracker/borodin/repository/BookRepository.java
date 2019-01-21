package com.netcracker.borodin.repository;

import com.netcracker.borodin.SQLConstant;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Genre;
import com.netcracker.borodin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  List<Book> findByTitle(String title);

  Optional<Book> getByTitle(String title);

  List<Book> findByGenres(Genre genre);

  List<Book> findByAuthors(Author author);

  List<Book> getAllByTitleContains(String title);

  List<Book> findTop100ByOrderByAverageRateDesc();

  List<Book> findFirst100ByGenresOrderByAverageRateDesc(Genre genre);

  List<Book> findFirst100ByAuthorsOrderByAverageRateDesc(Author author);

  @Modifying
  @Query(value = SQLConstant.UPDATE_AVERAGE_RATE, nativeQuery = true)
  void updateAverageRate(long bookId, double rate);

  List<Book> findBooksByUsers(User user);
}
