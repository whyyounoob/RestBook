package com.netcracker.borodin.controller;

import com.netcracker.borodin.details.UserDetailsImpl;
import com.netcracker.borodin.dto.UserBookDTO;
import com.netcracker.borodin.dto.UserBookForm;
import com.netcracker.borodin.service.implement.UserBookServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/list")
public class UserBookController {

  private final UserBookServiceImpl userBookServiceImpl;

  @Autowired
  public UserBookController(UserBookServiceImpl userBookServiceImpl) {
    this.userBookServiceImpl = userBookServiceImpl;
  }

  @ApiOperation(value = "Gets my books", nickname = "UserBookController.getMyBooks")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Books")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<UserBookDTO>> getMyBooks(Authentication authentication) {
    if (authentication != null) {
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<UserBookDTO> books = userBookServiceImpl.getMyBooks(userDetails.getId());
      if (CollectionUtils.isEmpty(books)) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      } else {
        return new ResponseEntity<>(books, HttpStatus.OK);
      }
    } else {
      return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
  }

  @ApiOperation(value = "Add my book", nickname = "UserBookController.addMyBook")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Added")})
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> addMyBook(
      @RequestBody UserBookForm userBookForm, Authentication authentication) {
    if (userBookForm.getRate() < 0 || userBookForm.getRate() > 10) {
      return new ResponseEntity<>("Bad rate", HttpStatus.BAD_REQUEST);
    } else {
      if (authentication != null) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userBookServiceImpl.addMyBook(userBookForm, userDetails.getId())) {
          return new ResponseEntity<>("Added", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
      } else {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
      }
    }
  }

  @ApiOperation(value = "Update your rate", nickname = "UserBookController.updateRate")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Your rate is updated")})
  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> updateRate(
      @RequestBody UserBookForm userBookForm, Authentication authentication) {
    if (authentication != null) {
      if (userBookForm.getRate() < 0 || userBookForm.getRate() > 10) {
        return new ResponseEntity<>("Bad rate", HttpStatus.BAD_REQUEST);
      } else {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userBookServiceImpl.updateRate(userBookForm, userDetails.getId())) {
          return new ResponseEntity<>("Your rate was update", HttpStatus.OK);
        } else {
          return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
      }
    } else {
      return new ResponseEntity<>("Authorize first", HttpStatus.UNAUTHORIZED);
    }
  }
}
