package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.entity.User;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class SignUpServiceImplTest {

  @Autowired private UserRepository userRepository;
  @Autowired private SignUpServiceImpl signUpService;

  @Test
  public void testAddUser() {
    when(userRepository.save(DataClass.goodUserWithoutId)).thenReturn(DataClass.goodUser);
    when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
    assertEquals(DataClass.goodUserDTO, signUpService.signUp(DataClass.goodUserForm));
  }

  @Test(expected = ResourceAlreadyExistException.class)
  public void testAddUserWithException() {
    when(userRepository.findByUsername("wrongUsername"))
        .thenReturn(Optional.of(User.builder().id(1L).build()));
    signUpService.signUp(DataClass.wrongUserForm);
  }
}
