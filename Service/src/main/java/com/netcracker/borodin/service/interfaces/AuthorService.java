package com.netcracker.borodin.service.interfaces;

import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.entity.Author;

import java.util.List;

/**
 * Service for work with {@link Author}
 *
 * @author Maxim Borodin
 */
public interface AuthorService {

  /**
   * This method for getting all authors.
   *
   * @return list of authors
   */
  List<AuthorDTO> findAllAuthors();

  /**
   * This method for getting one author by id
   *
   * @param id - id of the author
   * @return author with needed id
   */
  AuthorDTO getAuthorById(long id);

  /**
   * This method for getting list authors with needed name
   *
   * @param name - name of author
   * @return list of authors
   */
  List<AuthorDTO> findAuthorByName(String name);

  /**
   * This method for adding new author
   *
   * @param name - name of new author
   * @return new author
   */
  AuthorDTO addAuthor(String name);
}
