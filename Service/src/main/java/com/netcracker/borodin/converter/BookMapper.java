package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface BookMapper {
    @Mappings({
            @Mapping(target = "id", source = "entity.id"),
            @Mapping(target = "title", source = "entity.title"),
            @Mapping(target = "year", source = "entity.year"),
            @Mapping(target = "averageRate", source = "entity.averageRate")
    })
    BookDTO bookToBookDTO(Book entity);

    @Mappings({
            @Mapping(target = "id", source = "dto.id"),
            @Mapping(target = "title", source = "dto.title"),
            @Mapping(target = "year", source = "dto.year"),
            @Mapping(target = "averageRate", source = "dto.averageRate")
    })
    Book bookDTOToBook(BookDTO dto);

    GenreDTO genreToGenreDTO(Genre entity);

    Genre genreDTOToGenre(GenreDTO dto);

    AuthorDTO authorToAuthorDTO(Author entity);

    Author authorDTOToAuthor(AuthorDTO dto);

    List<AuthorDTO> authorsListToAuthorsDTOList(List<Author> entity);

    List<Author> authorsDTOListToAuthorsList(List<AuthorDTO> dto);

    List<GenreDTO> genreListToGenreDTOList(List<Genre> entity);

    List<Genre> genreDTOListToGenreList(List<GenreDTO> dto);

    List<BookDTO> bookListToBookDTOList(List<Book> list);
}
