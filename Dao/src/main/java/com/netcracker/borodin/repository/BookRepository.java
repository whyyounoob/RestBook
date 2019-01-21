package com.netcracker.borodin.repository;

import com.netcracker.borodin.SQLConstant;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Genre;
import com.netcracker.borodin.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    Book save(Book book);

    Optional<Book> findById(Long aLong);

    List<Book> findByTitle(String title);

    @Query(value = SQLConstant.GET_AVERAGE_RATE,
            nativeQuery = true)
    Optional<BigDecimal> getAverageRate(Long id);

    List<Book> findByGenres(Genre genre);

    List<Book> findByAuthors(Author author);

    List<Book> findTop100ByOrderByAverageRateDesc();

    List<Book> findFirst100ByGenresOrderByAverageRateDesc(Genre genre);

    List<Book> findFirst100ByAuthorsOrderByAverageRateDesc(Author author);

    @Modifying
    @Query(value = SQLConstant.UPDATE_AVERAGE_RATE,
            nativeQuery = true)
    void updateAverageRate(long bookId, double rate);

    List<Book> findBooksByUsers(User user);
}
