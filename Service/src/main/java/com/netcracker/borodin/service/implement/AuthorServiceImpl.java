package com.netcracker.borodin.service.implement;

import com.netcracker.borodin.converter.AuthorMapper;
import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import com.netcracker.borodin.repository.AuthorRepository;
import com.netcracker.borodin.service.interfaces.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
  private AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

  @Autowired
  public AuthorServiceImpl(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  public List<AuthorDTO> findAllAuthors() {
    log.debug("Start getting all authors from DB");
    return authorMapper.authorListToAuthorDTOList(authorRepository.findAll());
  }

  @Override
  public AuthorDTO getAuthorById(long id) {
    log.debug("Start getting author with id {}", id);
    Optional<Author> author = authorRepository.findById(id);
    if (author.isPresent()) {
      log.debug("Author with needed id {}", author.get());
      return authorMapper.authorToAuthorDTO(author.get());
    } else {
      log.debug("Author was not found with this id: {}", id);
      throw new ResourceNotFoundException("Author was not found with this id: " + id);
    }
    //    return authorMapper.authorToAuthorDTO(
    //        author.orElseThrow(
    //            () -> new ResourceNotFoundException("Author was not found with this id: " + id)));
  }

  @Override
  public List<AuthorDTO> findAuthorByName(String name) {
    log.debug("Start getting author by name {}", name);
    return authorMapper.authorListToAuthorDTOList(authorRepository.getAllByNameContains(name));
  }

  @Override
  public AuthorDTO addAuthor(String name) {
    log.debug("Start creating new author with name {}", name);
    Optional<Author> author = authorRepository.findByName(name);
    if (author.isPresent()) {
      log.debug("Author with this name {} is already contains in DB", name);
      throw new ResourceAlreadyExistException("Author with name = " + name + " is already exist");
    } else {
      log.debug("Adding new author");
      return authorMapper.authorToAuthorDTO(
          authorRepository.save(Author.builder().name(name).build()));
    }
  }
}
