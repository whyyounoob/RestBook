package com.netcracker.borodin.service.interfaces;

import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.entity.Genre;

import java.util.List;

/**
 * Service for work with {@link Genre}
 *
 * @author Maxim Borodin
 */
public interface GenreService {
  /**
   * This method for retrieving all genres.
   *
   * @return list of genres
   */
  List<GenreDTO> findAllGenres();

  /**
   * This method for retrieving one genre by id
   *
   * @param id - id of the genre
   * @return genre with needed id
   */
  GenreDTO getGenreById(long id);

  /**
   * This method for retrieving list genres with needed name
   *
   * @param name - name of genre
   * @return list of genres
   */
  List<GenreDTO> findGenreByName(String name);
  /**
   * This method for adding new genre
   *
   * @param name - name of new genre
   * @return id of new genre, if genre with this name is already contains return it id
   */
  GenreDTO addGenre(String name);
}
