package com.netcracker.borodin.converter;

import com.netcracker.borodin.configuration.DataClass;
import com.netcracker.borodin.configuration.TestConfiguration;
import com.netcracker.borodin.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class UserMapperTest {

  @Autowired private UserMapper userMapperImpl;

    @Test
    public void toDTO(){
        UserDTO actual = userMapperImpl.userToUserDTO(DataClass.goodUser);
        assertEquals(DataClass.goodUserDTO.hashCode(), actual.hashCode());
    }

}
