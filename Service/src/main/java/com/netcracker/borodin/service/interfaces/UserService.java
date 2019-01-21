package com.netcracker.borodin.service.interfaces;

import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.entity.User;

import java.util.List;

/**
 * Service for work with {@link User}
 *
 * @author Maxim Borodin
 */
public interface UserService {

  /**
   * This method for getting all users from DB
   *
   * @return list of users
   */
  List<UserDTO> findAllUsers();

  /**
   * This method for getting user with needed id
   *
   * @param id - user`s id
   * @return info about user
   */
  UserDTO getUserById(Long id);

  /**
   * This method for getting user with needed username
   *
   * @param username - user`s username
   * @return info about user
   */
  UserDTO getUserByUsername(String username);

  /**
   * This method for changing role from user to admin
   *
   * @param id - user`s id
   * @return true if role was changed
   */
  boolean makeAdmin(long id);

  /**
   * This method for changing state for user with needed id
   *
   * @param id - user`s id
   * @return true if state was changed
   */
  boolean changeState(long id);
}
