package com.netcracker.borodin.controller;

import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.dto.UserFormDTO;
import com.netcracker.borodin.service.implement.SignUpServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class SignUpController {

  private final PasswordEncoder passwordEncoder;

  private SignUpServiceImpl signUpServiceImpl;

  @Autowired
  public SignUpController(SignUpServiceImpl signUpServiceImpl,
                          PasswordEncoder passwordEncoder) {
    this.signUpServiceImpl = signUpServiceImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @ApiOperation(value = "Add user", nickname = "SignUpController.signUp")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "User")})
  @PostMapping("/signUp")
  public ResponseEntity<UserDTO> signUp(UserFormDTO userFormDTO) {
    String hashPassword = passwordEncoder.encode(userFormDTO.getPassword());
    userFormDTO.setPassword(hashPassword);
    UserDTO userDTO = signUpServiceImpl.signUp(userFormDTO);
    return new ResponseEntity<>(userDTO, HttpStatus.OK);
  }
}
