package com.netcracker.borodin.converter;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.dto.CommentDTO;
import com.netcracker.borodin.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class CommentMapperTest {
  @Autowired private CommentMapper commentMapperImpl;

  @Test
  public void toDTO() {
    CommentDTO commentDTO = commentMapperImpl.commentToCommentDTO(DataClass.goodComment);
    assertEquals(DataClass.goodCommentDTO.hashCode(), commentDTO.hashCode());
  }

  @Test
  public void fromDTO() {
    Comment comment = commentMapperImpl.commentDTOToComment(DataClass.goodCommentDTO);
    assertEquals(DataClass.goodComment.getId(), comment.getId());
    assertEquals(DataClass.goodComment.getText(), comment.getText());
  }
}
