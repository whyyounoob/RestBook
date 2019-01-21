package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.dto.UserBookDTO;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Genre;
import com.netcracker.borodin.entity.UserBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserBookMapper {
  @Mappings({@Mapping(target = "rate", source = "entity.rate")})
  UserBookDTO userBookToUserBookDTO(UserBook entity);

  BookDTO bookToBookDTO(Book entity);

  Book bookDTOToBook(BookDTO dto);

  AuthorDTO authorToAuthorDTO(Author entity);

  Author authorDTOToAuthor(AuthorDTO dto);

  GenreDTO genreToGenreDTO(Genre entity);

  Genre genreDTOToGenre(GenreDTO dto);
}
