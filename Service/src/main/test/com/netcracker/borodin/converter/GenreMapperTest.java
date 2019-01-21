package com.netcracker.borodin.converter;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.entity.Genre;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class GenreMapperTest {

  @Autowired private GenreMapper genreMapperImpl;

  @Test
  public void toDTO() {
    GenreDTO genreDTO = genreMapperImpl.genreToGenreDTO(DataClass.goodGenre);
    assertEquals(DataClass.goodGenreDTO.hashCode(), genreDTO.hashCode());
  }

  @Test
  public void fromDTO() {
    Genre genre = genreMapperImpl.genreDTOToGenre(DataClass.goodGenreDTO);
    assertEquals(DataClass.goodGenre.hashCode(), genre.hashCode());
  }
}
