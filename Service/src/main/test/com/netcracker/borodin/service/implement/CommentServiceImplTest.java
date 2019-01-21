package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.dto.CommentDTO;
import com.netcracker.borodin.entity.Comment;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.BookRepository;
import com.netcracker.borodin.repository.CommentRepository;
import com.netcracker.borodin.repository.UserRepository;
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
public class CommentServiceImplTest {
  @Autowired private CommentServiceImpl commentServiceImpl;
  @Autowired private CommentRepository commentRepository;
  @Autowired private BookRepository bookRepository;
  @Autowired private UserRepository userRepository;

  @Test
  public void testGetAll() {
    List<Comment> mockList = Collections.singletonList(DataClass.goodComment);
    List<CommentDTO> expectedAnswer = Collections.singletonList(DataClass.goodCommentDTO);

    when(commentRepository.findAll()).thenReturn(mockList);
    assertEquals(expectedAnswer, commentServiceImpl.findAllComments());
  }

  @Test(expected = ResourceNotFoundException.class)
  public void testAddWithWrongBook() {
    when(bookRepository.findById(1L)).thenReturn(Optional.empty());
    commentServiceImpl.addComment(DataClass.goodCommentForm);
  }

}
