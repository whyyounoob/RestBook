package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.converter.BookMapper;
import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.AuthorRepository;
import com.netcracker.borodin.repository.BookRepository;
import com.netcracker.borodin.repository.GenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class BookServiceImplTest {

  @Autowired private BookRepository bookRepository;
  @Autowired private BookServiceImpl bookServiceImpl;
  @Autowired private GenreRepository genreRepository;
  @Autowired private AuthorRepository authorRepository;
  @Autowired private BookMapper bookMapper;

  @Test
  public void testGetAll() {
    Book book1 = Book.builder().id(1L).title("title").build();
    Book book2 = Book.builder().id(2L).title("title2").build();
    List books = new ArrayList();
    books.add(book1);
    books.add(book2);
    List<BookDTO> expectedBookDTOS = bookMapper.bookListToBookDTOList(books);
    when(bookRepository.findAll()).thenReturn(books);
    List<BookDTO> actualBookDTOS = bookServiceImpl.findAllBook();
    assertEquals(expectedBookDTOS, actualBookDTOS);
    assertEquals(expectedBookDTOS.size(), actualBookDTOS.size());
  }

  @Test
  public void testGetById() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(DataClass.goodBook));
    BookDTO actualBookDTO = bookServiceImpl.getBookById(1L);
    assertEquals(DataClass.goodBookDTO, actualBookDTO);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testGetByIdWithException() {
    when(bookRepository.findById(1L)).thenReturn(Optional.empty());
    bookServiceImpl.getBookById(1L);
  }

  @Test
  public void testGetByTitle() {
    when(bookRepository.getAllByTitleContains("title"))
        .thenReturn(Collections.singletonList(DataClass.goodBook));
    List<BookDTO> actualBookDTO = bookServiceImpl.findBookByTitle("title");
    List<BookDTO> expectedBookDTO = new ArrayList<>();
    expectedBookDTO.add(DataClass.goodBookDTO);

    assertEquals(expectedBookDTO, actualBookDTO);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testGetByGenreIdWithException() {
    when(genreRepository.findById(1L)).thenReturn(Optional.empty());
    bookServiceImpl.findBookByGenreId(1L);
  }

  @Test
  public void testGetByGenreId() {
    when(genreRepository.findById(1L)).thenReturn(Optional.of(DataClass.goodGenre));
    when(bookRepository.findByGenres(DataClass.goodGenre))
        .thenReturn(Collections.singletonList(DataClass.goodBook));
    List<BookDTO> expectedList = new ArrayList<>();
    expectedList.add(DataClass.goodBookDTO);
    assertEquals(expectedList, bookServiceImpl.findBookByGenreId(1L));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testGetByGenreNameWithException() {
    when(genreRepository.findByName("name")).thenReturn(Optional.empty());
    bookServiceImpl.findBookByGenreName("name");
  }

  @Test
  public void testGetByGenreName() {
    when(genreRepository.findByName("genreName")).thenReturn(Optional.of(DataClass.goodGenre));
    when(bookRepository.findByGenres(DataClass.goodGenre))
        .thenReturn(Collections.singletonList(DataClass.goodBook));
    List<BookDTO> expectedList = new ArrayList<>();
    expectedList.add(DataClass.goodBookDTO);
    assertEquals(expectedList, bookServiceImpl.findBookByGenreName("genreName"));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testGetByAuthorIdWithException() {
    when(authorRepository.findById(1L)).thenReturn(Optional.empty());
    bookServiceImpl.findBookByAuthorId(1L);
  }

  @Test
  public void testGetByAuthorId() {
    when(authorRepository.findById(1L)).thenReturn(Optional.of(DataClass.goodAuthor));
    when(bookRepository.findByAuthors(DataClass.goodAuthor))
        .thenReturn(Collections.singletonList(DataClass.goodBook));
    List<BookDTO> expectedList = new ArrayList<>();
    expectedList.add(DataClass.goodBookDTO);
    assertEquals(expectedList, bookServiceImpl.findBookByAuthorId(1L));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testGetByAuthorNameWithException() {
    when(authorRepository.findByName("name")).thenReturn(Optional.empty());
    bookServiceImpl.findBookByAuthorName("name");
  }

  @Test
  public void testGetByAuthorName() {
    when(authorRepository.findByName("authorName")).thenReturn(Optional.of(DataClass.goodAuthor));
    when(bookRepository.findByAuthors(DataClass.goodAuthor))
        .thenReturn(Collections.singletonList(DataClass.goodBook));
    List<BookDTO> expectedList = new ArrayList<>();
    expectedList.add(DataClass.goodBookDTO);
    assertEquals(expectedList, bookServiceImpl.findBookByAuthorName("authorName"));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testAddWithWrongGenre() {
    when(genreRepository.findByName("wrongGenre")).thenReturn(Optional.empty());
    bookServiceImpl.addBook(DataClass.wrongGenreBookForm);
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testAddWithWrongAuthor() {
    when(authorRepository.findByName("wrongAuthor")).thenReturn(Optional.empty());
    when(genreRepository.findByName("genreName")).thenReturn(Optional.of(DataClass.goodGenre));
    bookServiceImpl.addBook(DataClass.wrongAuthorBookForm);
  }

  @Test(expected = ResourceAlreadyExistException.class)
  public void testAddWithWrongTitle() {
    when(authorRepository.findByName("authorName")).thenReturn(Optional.of(DataClass.goodAuthor));
    when(genreRepository.findByName("genreName")).thenReturn(Optional.of(DataClass.goodGenre));
    when(bookRepository.getByTitle("wrongTitle")).thenReturn(Optional.of(new Book()));
    bookServiceImpl.addBook(DataClass.wrongTitleBookForm);
  }

  @Test
  public void testAddBook() {
    when(authorRepository.findByName("authorName")).thenReturn(Optional.of(DataClass.goodAuthor));
    when(genreRepository.findByName("genreName")).thenReturn(Optional.of(DataClass.goodGenre));
    when(bookRepository.getByTitle("title")).thenReturn(Optional.empty());
    when(bookRepository.save(DataClass.goodBookWithoutId)).thenReturn(DataClass.goodBook);
    BookDTO actualBookDTO = bookServiceImpl.addBook(DataClass.goodBookForm);
    assertEquals(DataClass.goodBookDTO, actualBookDTO);
  }
}
