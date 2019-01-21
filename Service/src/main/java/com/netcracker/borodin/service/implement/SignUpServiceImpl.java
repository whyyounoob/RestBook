package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.converter.UserMapper;
import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.dto.UserFormDTO;
import com.netcracker.borodin.entity.User;
import com.netcracker.borodin.entity.enums.Role;
import com.netcracker.borodin.entity.enums.State;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.repository.UserRepository;
import com.netcracker.borodin.service.interfaces.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SignUpServiceImpl implements SignUpService {

  private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  private final UserRepository userRepository;

  @Autowired
  public SignUpServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDTO signUp(UserFormDTO userFormDTO) {
    log.debug("Start creating of new user");
    if (userRepository.findByUsername(userFormDTO.getUsername()).isPresent()) {
      log.debug(
          "Stop creating of new user, because this username {} already exist",
          userFormDTO.getUsername());
      throw new ResourceAlreadyExistException(
          "User with username: " + userFormDTO.getUsername() + " already exist");
    }
    User user =
        User.builder()
            .username(userFormDTO.getUsername())
            .hashPassword(userFormDTO.getPassword())
            .role(Role.USER)
            .state(State.ACTIVE)
            .build();
    log.debug("Create new user {}", user);
    return userMapper.userToUserDTO(userRepository.save(user));
  }
}
