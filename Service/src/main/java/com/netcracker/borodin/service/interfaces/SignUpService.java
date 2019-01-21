package com.netcracker.borodin.service.interfaces;

import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.dto.UserFormDTO;

public interface SignUpService {

  /**
   * This method creating new user in DB
   *
   * @param userFormDTO - info about new user
   * @return information about user
   */
  UserDTO signUp(UserFormDTO userFormDTO);
}
