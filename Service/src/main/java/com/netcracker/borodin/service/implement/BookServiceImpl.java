package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.converter.BookMapper;
import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.dto.BookFormDTO;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Genre;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.AuthorRepository;
import com.netcracker.borodin.repository.BookRepository;
import com.netcracker.borodin.repository.GenreRepository;
import com.netcracker.borodin.service.interfaces.BookService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final GenreRepository genreRepository;
  private final AuthorRepository authorRepository;

  private BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

  @Autowired
  public BookServiceImpl(
      BookRepository bookRepository,
      GenreRepository genreRepository,
      AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.genreRepository = genreRepository;
    this.authorRepository = authorRepository;
  }

  @Override
  public BookDTO addBook(BookFormDTO bookFormDTO) {
    List<Genre> genres = new ArrayList<>();
    List<Author> authors = new ArrayList<>();
    log.debug("Start checking genres of new book");
    for (String genreName : bookFormDTO.getGenres()) {
      log.debug("Check genre with name {}", genreName);
      Optional<Genre> genre = genreRepository.findByName(genreName);
      if (genre.isPresent()) {
        log.debug("Genre with this name: {} exist in DB", genre.get().getName());
        genres.add(genre.get());
      } else {
        throw new ResourceNotFoundException("Genre with this name: " + genreName + " not found");
      }
      //      genres.add(
      //          genre.orElseThrow(
      //              () ->
      //                  new ResourceNotFoundException(
      //                      "Genre with this name: " + genreDTO.getName() + " not found")));
    }
    log.debug("Finish checking genres of new book");
    log.debug("Start checking authors of new book");
    for (String authorName : bookFormDTO.getAuthors()) {
      log.debug("Check author with name {}", authorName);
      Optional<Author> author = authorRepository.findByName(authorName);
      if (author.isPresent()) {
        log.debug("Author with this name: {} exist in DB", author.get().getName());
        authors.add(author.get());
      } else {
        throw new ResourceNotFoundException("Author with this name: " + authorName + " not found");
      }
      //      authors.add(
      //          author.orElseThrow(
      //              () ->
      //                  new ResourceNotFoundException(
      //                      "Author with this name: " + authorDTO.getName() + " not found")));
    }
    log.debug("Finish checking authors of new book");
    log.debug("Check book with this title: {}", bookFormDTO.getTitle());
    Optional<Book> checkBook = bookRepository.getByTitle(bookFormDTO.getTitle());
    if (checkBook.isPresent()) {
      throw new ResourceAlreadyExistException(
          "Book with title: " + bookFormDTO.getTitle() + " is already exist");
    } else {
      Book book =
          Book.builder()
              .title(bookFormDTO.getTitle())
              .authors(authors)
              .genres(genres)
              .year(bookFormDTO.getYear())
              .build();
      log.debug("Add book {}", book.toString());
      return bookMapper.bookToBookDTO(bookRepository.save(book));
    }
  }

  @Override
  public List<BookDTO> findAllBook() {
    log.debug("Getting all books from DB");
    return bookMapper.bookListToBookDTOList(bookRepository.findAll());
  }

  @Override
  public BookDTO getBookById(long id) {
    log.debug("Start getting book with id {}", id);
    Optional<Book> book = bookRepository.findById(id);
    if (book.isPresent()) {
      log.debug("Book with needed id {}", book.get());
      return bookMapper.bookToBookDTO(book.get());
    } else {
      log.debug("Book was not found with this id: {}", id);
      throw new ResourceNotFoundException("Book was not found with this id: " + id);
    }
    //    return bookMapper.bookToBookDTO(
    //        book.orElseThrow(
    //            () -> new ResourceNotFoundException("Book was not found with this id: " + id)));
  }

  @Override
  public List<BookDTO> findBookByTitle(String title) {
    log.debug("Start getting books by title{}", title);
    return bookMapper.bookListToBookDTOList(bookRepository.getAllByTitleContains(title));
  }

  @Override
  public List<BookDTO> findBookByGenreId(long id) {
    log.debug("Start retrieving books by genre id {}", id);
    Optional<Genre> genre = genreRepository.findById(id);
    if (genre.isPresent()) {
      log.debug("Genre with needed id {}", genre.get());
      return bookMapper.bookListToBookDTOList(bookRepository.findByGenres(genre.get()));
    } else {
      log.debug("Genre with this id {} not found", id);
      throw new ResourceNotFoundException("Genre with this id = " + id + " not found");
    }
    //    return bookMapper.bookListToBookDTOList(
    //        bookRepository.findByGenres(
    //            genre.orElseThrow(
    //                () -> new ResourceNotFoundException("Genre with this id = " + id + " not
    // found"))));
  }

  @Override
  public List<BookDTO> findBookByGenreName(String name) {
    log.debug("Start retrieving books by genre name {} ", name);
    Optional<Genre> genre = genreRepository.findByName(name);
    if (genre.isPresent()) {
      log.debug("Genre with needed name {}", genre.get());
      return bookMapper.bookListToBookDTOList(bookRepository.findByGenres(genre.get()));
    } else {
      log.debug("Genre with this name {} not found", name);
      throw new ResourceNotFoundException("Genre with this id = " + name + " not found");
    }
    //    return bookMapper.bookListToBookDTOList(
    //        bookRepository.findByGenres(
    //            genre.orElseThrow(
    //                () ->
    //                    new ResourceNotFoundException(
    //                        "Genre with this name = " + name + " not found"))));
  }

  @Override
  public List<BookDTO> findBookByAuthorId(long id) {
    log.debug("Start retrieving books by author id {}", id);
    Optional<Author> author = authorRepository.findById(id);
    if (author.isPresent()) {
      log.debug("Author with needed id {}", author.get());
      return bookMapper.bookListToBookDTOList(bookRepository.findByAuthors(author.get()));
    } else {
      log.debug("Author with this id {} not found", id);
      throw new ResourceNotFoundException("Author with this id = " + id + " not found");
    }
    //    return bookMapper.bookListToBookDTOList(
    //        bookRepository.findByAuthors(
    //            author.orElseThrow(
    //                () ->
    //                    new ResourceNotFoundException("Author with this id = " + id + " not
    // found"))));
  }

  @Override
  public List<BookDTO> findBookByAuthorName(String name) {
    log.debug("Start retrieving books by author name {}", name);
    Optional<Author> author = authorRepository.findByName(name);
    if (author.isPresent()) {
      log.debug("Author with needed name {}", author.get());
      return bookMapper.bookListToBookDTOList(bookRepository.findByAuthors(author.get()));
    } else {
      log.debug("Author with this name {} not found", name);
      throw new ResourceNotFoundException("Author with this name = " + name + " not found");
    }
    //    return bookMapper.bookListToBookDTOList(
    //        bookRepository.findByAuthors(
    //            author.orElseThrow(
    //                () ->
    //                    new ResourceNotFoundException(
    //                        "Author with this name = " + name + " not found"))));
  }

  @Override
  public List<BookDTO> getTop() {
    log.debug("Getting top 100 by average rate");
    return bookMapper.bookListToBookDTOList(bookRepository.findTop100ByOrderByAverageRateDesc());
  }

  @Override
  public List<BookDTO> getTopByGenre(String name) {
    log.debug("Start getting top 100 books by genre: {}", name);
    Optional<Genre> genre = genreRepository.findByName(name);
    if (genre.isPresent()) {
      log.debug("Genre with needed name {}", genre.get());
      return bookMapper.bookListToBookDTOList(
          bookRepository.findFirst100ByGenresOrderByAverageRateDesc(genre.get()));
    } else {
      log.debug("Genre with this name {} not found", name);
      throw new ResourceNotFoundException("Genre with this name = " + name + " not found");
    }
    //    return bookMapper.bookListToBookDTOList(
    //        bookRepository.findFirst100ByGenresOrderByAverageRateDesc(
    //            genre.orElseThrow(
    //                () ->
    //                    new ResourceNotFoundException(
    //                        "Genre with this name: " + name + " not fount"))));
  }

  @Override
  public List<BookDTO> getTopByAuthor(String name) {
    log.debug("Start getting top 100 books by author: {}", name);
    Optional<Author> author = authorRepository.findByName(name);
    if (author.isPresent()) {
      log.debug("Author with needed name {}", author.get());
      return bookMapper.bookListToBookDTOList(
          bookRepository.findFirst100ByAuthorsOrderByAverageRateDesc(author.get()));
    } else {
      log.debug("Author with this name {} not found", name);
      throw new ResourceNotFoundException("Author with this name = " + name + " not found");
    }
    //    return bookMapper.bookListToBookDTOList(
    //        bookRepository.findFirst100ByAuthorsOrderByAverageRateDesc(
    //            author.orElseThrow(
    //                () ->
    //                    new ResourceNotFoundException(
    //                        "Author with this name: " + name + " not found"))));
  }
}
