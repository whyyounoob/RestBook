package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.converter.UserMapper;
import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.entity.User;
import com.netcracker.borodin.entity.enums.Role;
import com.netcracker.borodin.entity.enums.State;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.exception.UnnecessaryActionException;
import com.netcracker.borodin.repository.UserRepository;
import com.netcracker.borodin.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<UserDTO> findAllUsers() {
    log.debug("Start retrieving all users");
    return userMapper.userListToUserDTOList(userRepository.findAll());
  }

  @Override
  public UserDTO getUserById(Long id) {
    log.debug("Start retrieving user with id {}", id);
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      log.debug("Find user with id {} - {}", id, user.get());
      return userMapper.userToUserDTO(user.get());
    } else {
      throw new ResourceNotFoundException("User with id " + id + " not found");
    }
    //    return user.map(user1 -> userMapper.userToUserDTO(user1))
    //        .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not
    // found"));
  }

  @Override
  public UserDTO getUserByUsername(String username) {
    log.debug("Start retrieving users with username {}", username);
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      log.debug("User with username {} was found - {}", username, user.get());
      return userMapper.userToUserDTO(user.get());
    } else {
      throw new ResourceNotFoundException("User with username: " + username + " not found");
      //      return user.map(user1 -> userMapper.userToUserDTO(user1))
      //          .orElseThrow(
      //              () ->
      //                  new ResourceNotFoundException("User with username: " + username + " not
      // found"));
    }
  }

  @Override
  public boolean makeAdmin(long id) {
    log.debug("Start change user role with id {}", id);
    Optional<User> findUser = userRepository.findById(id);
    if (findUser.isPresent()) {
      User user = findUser.get();
      log.debug("user with id exist");
      if (user.getRole().equals(Role.ADMIN)) {
        log.debug("Unnecessary actions, this user {} already admin", user);
        throw new UnnecessaryActionException("User with id " + id + " already admin");
      } else {
        user.setRole(Role.ADMIN);
        userRepository.update(user.getId(), user.getRole().toString(), user.getState().toString());
        log.debug("Role updated on user with id {}", id);
        return true;
      }
    } else {
      log.debug("User with id {} not found", id);
      throw new ResourceNotFoundException("User with id " + id + " not found");
    }
  }

  @Override
  public boolean changeState(long id) {
    log.debug("Start changing state for user with id {}", id);
    Optional<User> findUser = userRepository.findById(id);
    if (findUser.isPresent()) {
      User user = findUser.get();
      log.debug("User with id {} exist", id);
      if (user.getState().equals(State.ACTIVE)) {
        log.debug("State changing from active to delete");
        user.setState(State.DELETE);
      } else {
        log.debug("State changing from delete to active");
        user.setState(State.ACTIVE);
      }
      userRepository.update(user.getId(), user.getRole().toString(), user.getState().toString());
      log.debug("State for user with id {} changed", id);
      return true;
    } else {
      log.debug("User with id {} not found", id);
      throw new ResourceNotFoundException("User with id " + id + " not found");
    }
  }
}
