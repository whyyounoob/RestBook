package com.netcracker.borodin.controller;

import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.service.implement.AuthorServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.net.ConnectException;
import java.util.List;

@Api
@RestController
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorServiceImpl authorServiceImpl;

  @Autowired
  public AuthorController(AuthorServiceImpl authorServiceImpl) {
    this.authorServiceImpl = authorServiceImpl;
  }

  @ApiOperation(value = "Gets all authors", nickname = "AuthorController.getAllAuthors")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Authors")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AuthorDTO>> getAllAuthors(){
    List<AuthorDTO> authors = authorServiceImpl.findAllAuthors();
    if (CollectionUtils.isEmpty(authors)) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(authors, HttpStatus.OK);
    }
  }

  @ApiOperation(value = "Gets specific author by id", nickname = "AuthorController.getAuthorById")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Author")})
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id) {
    AuthorDTO author = authorServiceImpl.getAuthorById(id);
    if (author == null) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(author, HttpStatus.OK);
  }

  @ApiOperation(
      value = "Gets specific author by name",
      nickname = "AuthorController.getAuthorByName")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Authors")})
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AuthorDTO>> getAuthorsByName(@RequestParam("name") String name) {
    List<AuthorDTO> authors = authorServiceImpl.findAuthorByName(name);
    if (CollectionUtils.isEmpty(authors)) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(authors, HttpStatus.OK);
  }

  @ApiOperation(value = "Creates author", nickname = "AuthorController.createAuthor")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Author is created")})
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthorDTO> createAuthor(@RequestBody String name) {
    AuthorDTO authorDTO = authorServiceImpl.addAuthor(name);
    return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
  }
}
