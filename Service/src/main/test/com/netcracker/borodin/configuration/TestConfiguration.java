package com.netcracker.borodin.configuration;

import com.netcracker.borodin.converter.*;
import com.netcracker.borodin.repository.*;
import com.netcracker.borodin.service.implement.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfiguration {
  @Bean
  public AuthorMapper authorMapper() {
    return Mappers.getMapper(AuthorMapper.class);
  }

  @Bean
  public GenreMapper genreMapper() {
    return Mappers.getMapper(GenreMapper.class);
  }

  @Bean
  public CommentMapper commentMapper() {
    return Mappers.getMapper(CommentMapper.class);
  }

  @Bean
  public UserMapper userMapper() {
    return Mappers.getMapper(UserMapper.class);
  }

  @Bean
  public GenreServiceImpl genreService() {
    return new GenreServiceImpl(genreRepository());
  }

  @Bean
  public GenreRepository genreRepository() {
    return mock(GenreRepository.class);
  }

  @Bean
  public AuthorRepository authorRepository() {
    return mock(AuthorRepository.class);
  }

  @Bean
  public AuthorServiceImpl authorService() {
    return new AuthorServiceImpl(authorRepository());
  }

  @Bean
  public BookRepository bookRepository() {
    return mock(BookRepository.class);
  }

  @Bean
  public BookServiceImpl bookService() {
    return new BookServiceImpl(bookRepository(), genreRepository(), authorRepository());
  }

  @Bean
  public BookMapper bookMapper() {
    return Mappers.getMapper(BookMapper.class);
  }

  @Bean
  public UserRepository userRepository() {
    return mock(UserRepository.class);
  }

  @Bean
  public SignUpServiceImpl signUpService() {
    return new SignUpServiceImpl(userRepository());
  }

  @Bean
  public CommentRepository commentRepository() {
    return mock(CommentRepository.class);
  }

  @Bean
  public CommentServiceImpl commentService() {
    return new CommentServiceImpl(commentRepository(), bookRepository(), userRepository());
  }
}
