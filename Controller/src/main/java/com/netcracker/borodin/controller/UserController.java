package com.netcracker.borodin.controller;

import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.service.implement.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/users")
public class UserController {

  private final UserServiceImpl userServiceImpl;

  @Autowired
  public UserController(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
  }

  @ApiOperation(value = "Gets all users", nickname = "UserController.getAllUsers")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Users")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Iterable<UserDTO>> getAllUsers() {
    Iterable<UserDTO> users = userServiceImpl.findAllUsers();
    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @ApiOperation(value = "Gets specific user by id", nickname = "UserController.getUserById")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "User")})
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
    UserDTO user = userServiceImpl.getUserById(id);
    if (user == null) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Gets specific user by username",
      nickname = "UserController.getUserBYUsername")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "User")})
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> getUserByUsername(@RequestParam("username") String username) {
    UserDTO user = userServiceImpl.getUserByUsername(username);
    if (user == null) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @ApiOperation(value = "Make user admin", nickname = "UserController.updateToAdmin")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Your rate is updated")})
  @PutMapping(
      value = "/admin",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> updateToAdmin(
      @RequestBody Long userId, Authentication authentication) {
    if (authentication != null) {
      if (userServiceImpl.makeAdmin(userId)) {
        return new ResponseEntity<>(
            "User with id" + userId.toString() + "now admin!", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
      }
    } else {
      return new ResponseEntity<>("Authorize first", HttpStatus.UNAUTHORIZED);
    }
  }

  @ApiOperation(value = "Change state for user", nickname = "UserController.changeState")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Your rate is updated")})
  @PutMapping(
      value = "/state",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> changeState(
      @RequestBody Long userId, Authentication authentication) {
    if (authentication != null) {
      if (userServiceImpl.changeState(userId)) {
        return new ResponseEntity<>(
            "User with id" + userId.toString() + "changed state", HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
      }
    } else {
      return new ResponseEntity<>("Authorize first", HttpStatus.UNAUTHORIZED);
    }
  }
}
