package com.netcracker.borodin.controller;

import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.dto.BookFormDTO;
import com.netcracker.borodin.service.implement.BookServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/books")
public class BookController {

  private final BookServiceImpl bookServiceImpl;

  @Autowired
  public BookController(BookServiceImpl bookServiceImpl) {
    this.bookServiceImpl = bookServiceImpl;
  }

  @ApiOperation(value = "Gets all books", nickname = "BookController.getAllBooks")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getAllBooks() {
    List<BookDTO> books = bookServiceImpl.findAllBook();
    if (CollectionUtils.isEmpty(books)) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(books, HttpStatus.OK);
    }
  }

  @ApiOperation(value = "Gets specific book by id", nickname = "BookController.getBookById")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Book")})
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
    BookDTO book = bookServiceImpl.getBookById(id);
    if (book == null) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  @ApiOperation(value = "Gets specific book by title", nickname = "BookController.getBookByTitle")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getBookByTitle(@RequestParam("title") String title) {
    List<BookDTO> books = bookServiceImpl.findBookByTitle(title);
    if (books.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Gets specific book by genreId",
      nickname = "BookController.getBookByGenreId")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(value = "/search/genreId", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getBookByGenreId(@RequestParam("id") long id) {
    List<BookDTO> books = bookServiceImpl.findBookByGenreId(id);
    if (books.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Gets specific book by genreName",
      nickname = "BookController.getBookByGenreName")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(value = "/search/genreName", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getBookByGenreName(@RequestParam("name") String name) {
    List<BookDTO> books = bookServiceImpl.findBookByGenreName(name);
    if (books.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Gets specific book by author id",
      nickname = "BookController.getBookByAuthorId")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(value = "/search/authorId", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getBookByAuthorId(@RequestParam("id") long id) {
    List<BookDTO> books = bookServiceImpl.findBookByAuthorId(id);
    if (books.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Gets specific book by author name",
      nickname = "BookController.getBookByAuthorName")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(value = "/search/authorName", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getBookByAuthorName(@RequestParam("name") String name) {
    List<BookDTO> books = bookServiceImpl.findBookByAuthorName(name);
    if (books.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @ApiOperation(value = "Creates book", nickname = "BookController.createBook")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Book is created")})
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<BookDTO> createBook(@RequestBody BookFormDTO bookFormDTO) {

    BookDTO bookDTO1 = bookServiceImpl.addBook(bookFormDTO);
    return new ResponseEntity<>(bookDTO1, HttpStatus.CREATED);
  }

  @ApiOperation(value = "Gets top100 books", nickname = "BookController.getTop")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(value = "/top", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getTop() {
    List<BookDTO> books = bookServiceImpl.getTop();
    if (books.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @ApiOperation(value = "Gets top100 books by genre", nickname = "BookController.getTopByGenre")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(value = "/top/genre", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getTopByGenre(@RequestParam("name") String name) {
    List<BookDTO> books = bookServiceImpl.getTopByGenre(name);
    if (books.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @ApiOperation(value = "Gets top100 books by author", nickname = "BookController.getTopByAuthor")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(value = "/top/author", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<BookDTO>> getTopByAuthor(@RequestParam("name") String name) {
    List<BookDTO> books = bookServiceImpl.getTopByAuthor(name);
    if (books.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(books, HttpStatus.OK);
  }
}
