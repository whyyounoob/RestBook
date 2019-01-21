package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.converter.GenreMapper;
import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.entity.Genre;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.GenreRepository;
import com.netcracker.borodin.service.interfaces.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GenreServiceImpl implements GenreService {

  private GenreRepository genreRepository;

  private GenreMapper genreMapper = Mappers.getMapper(GenreMapper.class);

  @Autowired
  public GenreServiceImpl(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  @Override
  public List<GenreDTO> findAllGenres() {
    log.debug("Start retrieving all genres");
    return genreMapper.listGenreToListGenreDTO(genreRepository.findAll());
  }

  @Override
  public GenreDTO getGenreById(long id) {
    log.debug("Start retrieving genre by id {}", id);
    Optional<Genre> genre = genreRepository.findById(id);
    if (genre.isPresent()) {
      log.debug("Genre with id {} - {}", id, genre.get());
      return genreMapper.genreToGenreDTO(genre.get());
    } else {
      log.debug("Genre with id {} not found", id);
      throw new ResourceNotFoundException("Genre with id: " + id + " not found");
    }
    //    return genreRepository
    //        .findById(id)
    //        .map(genre1 -> genreMapper.genreToGenreDTO(genre1))
    //        .orElseThrow(() -> new ResourceNotFoundException("Genre with id: " + id + " not
    // found"));
  }

  @Override
  public List<GenreDTO> findGenreByName(String name) {
    log.debug("Start retrieving genres by name {}", name);
    return genreMapper.listGenreToListGenreDTO(genreRepository.getAllByNameContains(name));
  }

  @Override
  public GenreDTO addGenre(String name) {
    log.debug("Start creating genre with name {}", name);
    Optional<Genre> genre = genreRepository.findByName(name);
    if (genre.isPresent()) {
      log.debug("Genre with this name {} already exist", name);
      throw new ResourceAlreadyExistException("Genre with this name: " + name + " already exist");
    } else {
      log.debug("Create new genre with name {}", name);
      return genreMapper.genreToGenreDTO(genreRepository.save(Genre.builder().name(name).build()));
    }
  }
}
