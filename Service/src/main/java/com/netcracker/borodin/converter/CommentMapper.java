package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.*;
import com.netcracker.borodin.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface CommentMapper {
  @Mappings({
    @Mapping(target = "id", source = "entity.id"),
    @Mapping(target = "text", source = "entity.text"),
    @Mapping(
        target = "createDate",
        source = "entity.createDate",
        dateFormat = "'Date:' yyyy.MM.dd  'Time:' HH:mm:ss"),
    @Mapping(
        target = "modifyDate",
        source = "entity.modifyDate",
        dateFormat = "'Date:' yyyy.MM.dd  'Time:' HH:mm:ss")
  })
  CommentDTO commentToCommentDTO(Comment entity);

  @Mappings({
    @Mapping(target = "id", source = "dto.id"),
    @Mapping(target = "text", source = "dto.text"),
    @Mapping(
        target = "createDate",
        source = "dto.createDate",
        dateFormat = "'Date:' yyyy.MM.dd  'Time:' HH:mm:ss"),
    @Mapping(
        target = "modifyDate",
        source = "dto.modifyDate",
        dateFormat = "'Date:' yyyy.MM.dd  'Time:' HH:mm:ss")
  })
  Comment commentDTOToComment(CommentDTO dto);

  BookDTO bookToBookDTO(Book entity);

  Book bookDTOToBook(BookDTO dto);

  UserDTO userToUserDTO(User entity);

  User userDTOToUser(UserDTO dto);

  AuthorDTO authorToAuthorDTO(Author entity);

  Author authorDTOToAuthor(AuthorDTO dto);

  GenreDTO genreToGenreDTO(Genre entity);

  Genre genreDTOToGenre(GenreDTO dto);

  List<AuthorDTO> authorsListToAuthorsDTOList(List<Author> entity);

  List<Author> authorsDTOListToAuthorsList(List<AuthorDTO> dto);

  List<GenreDTO> genreListToGenreDTOList(List<Genre> entity);

  List<Genre> genreDTOListToGenreList(List<GenreDTO> dto);

  List<CommentDTO> commentListToCommentDTOList(List<Comment> list);
}
