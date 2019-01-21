package com.netcracker.borodin.service.interfaces;

import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.dto.BookFormDTO;
import com.netcracker.borodin.entity.Book;

import java.util.List;

/**
 * Service for work with {@link Book}
 *
 * @author Maxim Borodin
 */
public interface BookService {

  /**
   * This method create new book in DB
   *
   * @param bookDTO - info about new book
   * @return info about new book
   */
  BookDTO addBook(BookFormDTO bookFormDTO);

  /**
   * This method for getting all books from DB.
   *
   * @return list of books
   */
  List<BookDTO> findAllBook();

  /**
   * This method for getting one book by id
   *
   * @param id - id of the book
   * @return book with needed id
   */
  BookDTO getBookById(long id);

  /**
   * This method for retrieving list of books with needed title
   *
   * @param title - title of book
   * @return list of books
   */
  List<BookDTO> findBookByTitle(String title);

  /**
   * This method fot retrieving list of book with needed genre id
   *
   * @param id - id of needed genre
   * @return list of book
   */
  List<BookDTO> findBookByGenreId(long id);

  /**
   * This method fot retrieving list of book with needed genre name
   *
   * @param name - name of needed genre
   * @return list of book
   */
  List<BookDTO> findBookByGenreName(String name);

  /**
   * This method fot retrieving list of book with needed author id
   *
   * @param id - id of needed author
   * @return list of book
   */
  List<BookDTO> findBookByAuthorId(long id);

  /**
   * This method fot retrieving list of book with needed author name
   *
   * @param name - name of needed author
   * @return list of book
   */
  List<BookDTO> findBookByAuthorName(String name);

  /**
   * This method for retrieving top 100 books by average rate
   *
   * @return list of books
   */
  List<BookDTO> getTop();

  /**
   * This method for retrieving top 100 books by average rate with needed genre
   *
   * @param name - name of needed genre
   * @return list of books
   */
  List<BookDTO> getTopByGenre(String name);

  /**
   * This method for retrieving top 100 books by average rate with needed author
   *
   * @param name - name of needed author
   * @return list of books
   */
  List<BookDTO> getTopByAuthor(String name);
}
