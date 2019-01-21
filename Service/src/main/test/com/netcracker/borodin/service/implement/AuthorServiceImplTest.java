package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.AuthorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class AuthorServiceImplTest {

  @Autowired private AuthorRepository authorRepository;
  @Autowired private AuthorServiceImpl authorServiceImpl;

  @Test
  public void testGetAll() {
    List<Author> mockList = Collections.singletonList(DataClass.goodAuthor);
    List<AuthorDTO> expectedAnswer = Collections.singletonList(DataClass.goodAuthorDTO);

    when(authorRepository.findAll()).thenReturn(mockList);
    assertEquals(expectedAnswer, authorServiceImpl.findAllAuthors());
  }

  @Test
  public void testGetById() {
    when(authorRepository.findById(1L)).thenReturn(Optional.of(DataClass.goodAuthor));
    assertEquals(DataClass.goodAuthorDTO, authorServiceImpl.getAuthorById(1L));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testGetByIdWithException() throws ResourceNotFoundException {
    when(authorRepository.findById(2L)).thenReturn(Optional.empty());
    authorServiceImpl.getAuthorById(2L);
  }

  @Test
  public void testGetByName() {
    List<Author> mockList = Collections.singletonList(DataClass.goodAuthor);
    List<AuthorDTO> expectedAnswer = Collections.singletonList(DataClass.goodAuthorDTO);
    when(authorRepository.getAllByNameContains("authorName")).thenReturn(mockList);
    assertEquals(expectedAnswer, authorServiceImpl.findAuthorByName("authorName"));
  }

  @Test
  public void testAddAuthor() {
    when(authorRepository.findByName("authorName")).thenReturn(Optional.empty());
    when(authorRepository.save(Author.builder().name("authorName").build()))
            .thenReturn(DataClass.goodAuthor);
    AuthorDTO actual = authorServiceImpl.addAuthor("authorName");
    assertEquals(DataClass.goodAuthorDTO, actual);
  }

  @Test(expected = ResourceAlreadyExistException.class)
  public void testAddAuthorWithException() {
    when(authorRepository.findByName("wrongName")).thenReturn(Optional.of(new Author()));
    authorServiceImpl.addAuthor("wrongName");
  }
}
