package com.netcracker.borodin.converter;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.entity.Author;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class AuthorMapperTest {

  @Autowired private AuthorMapper authorMapperImpl;

  @Test
  public void toDTO() {
    AuthorDTO authorDTO = authorMapperImpl.authorToAuthorDTO(DataClass.goodAuthor);
    assertEquals(DataClass.goodAuthorDTO.hashCode(), authorDTO.hashCode());
  }

  @Test
  public void fromDTO() {
    Author author = authorMapperImpl.authorDTOToAuthor(DataClass.goodAuthorDTO);
    assertEquals(DataClass.goodAuthor.hashCode(), author.hashCode());
  }
}
