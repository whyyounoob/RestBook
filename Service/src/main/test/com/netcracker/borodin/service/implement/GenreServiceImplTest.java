package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.entity.Genre;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.GenreRepository;
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
public class GenreServiceImplTest {

  @Autowired private GenreRepository genreRepository;
  @Autowired private GenreServiceImpl genreServiceImpl;

  @Test
  public void testGetAll() {
    List<Genre> mockList = Collections.singletonList(DataClass.goodGenre);
    List<GenreDTO> expectedAnswer = Collections.singletonList(DataClass.goodGenreDTO);

    when(genreRepository.findAll()).thenReturn(mockList);
    assertEquals(expectedAnswer, genreServiceImpl.findAllGenres());
  }

  @Test
  public void testGetById() {
    when(genreRepository.findById(1L)).thenReturn(Optional.of(DataClass.goodGenre));
    assertEquals(DataClass.goodGenreDTO, genreServiceImpl.getGenreById(1L));
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testGetByIdWithException() {
    when(genreRepository.findById(2L)).thenReturn(Optional.empty());
    genreServiceImpl.getGenreById(2L);
  }

  @Test
  public void testGetByName() {
    List<Genre> mockList = Collections.singletonList(DataClass.goodGenre);
    List<GenreDTO> expectedAnswer = Collections.singletonList(DataClass.goodGenreDTO);
    when(genreRepository.getAllByNameContains("genreName")).thenReturn(mockList);
    assertEquals(expectedAnswer, genreServiceImpl.findGenreByName("genreName"));
  }

  @Test
  public void testAddGenre() {
    when(genreRepository.findByName("genreName")).thenReturn(Optional.empty());
    when(genreRepository.save(Genre.builder().name("genreName").build()))
        .thenReturn(DataClass.goodGenre);
    GenreDTO actual = genreServiceImpl.addGenre("genreName");
    assertEquals(DataClass.goodGenreDTO, actual);
  }

  @Test(expected = ResourceAlreadyExistException.class)
  public void testAddGenreWithException() {
    when(genreRepository.findByName("wrongName")).thenReturn(Optional.of(new Genre()));
    genreServiceImpl.addGenre("wrongName");
  }
}
