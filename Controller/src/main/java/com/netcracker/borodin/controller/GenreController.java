package com.netcracker.borodin.controller;

import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.service.implement.GenreServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/genres")
public class GenreController {

  private final GenreServiceImpl genreServiceImpl;

  @Autowired
  public GenreController(GenreServiceImpl genreServiceImpl) {
    this.genreServiceImpl = genreServiceImpl;
  }

  @ApiOperation(value = "Gets all genres", nickname = "GenreController.getAllGenres")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Genres")})
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Iterable<GenreDTO>> getAllGenres() {
    Iterable<GenreDTO> genres = genreServiceImpl.findAllGenres();
    return new ResponseEntity<>(genres, HttpStatus.OK);
  }

  @ApiOperation(value = "Gets specific genre by id", nickname = "GenreController.getGenreById")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Genre")})
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GenreDTO> getGenreById(@PathVariable("id") Long id) {
    GenreDTO genre = genreServiceImpl.getGenreById(id);
    if (genre == null) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(genre, HttpStatus.OK);
  }

  @ApiOperation(value = "Gets specific genre by name", nickname = "GenreController.getGenreByName")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Genres")})
  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<GenreDTO>> getGenresByName(@RequestParam("name") String name) {
    List<GenreDTO> genres = genreServiceImpl.findGenreByName(name);
    if (genres.size() == 0) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(genres, HttpStatus.OK);
  }

  @ApiOperation(value = "Creates genre", nickname = "GenreController.createGenre")
  @ApiResponses(value = {@ApiResponse(code = 201, message = "Genre is created")})
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GenreDTO> createGenre(@RequestBody String name) {
    GenreDTO genreDTO = genreServiceImpl.addGenre(name);
    return new ResponseEntity<>(genreDTO, HttpStatus.CREATED);
  }
}
