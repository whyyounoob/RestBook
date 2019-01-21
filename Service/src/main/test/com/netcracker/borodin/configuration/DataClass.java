package com.netcracker.borodin.configuration;

import com.netcracker.borodin.dto.*;
import com.netcracker.borodin.entity.*;
import com.netcracker.borodin.entity.enums.Role;
import com.netcracker.borodin.entity.enums.State;

import java.util.Collections;
import java.util.Date;

public class DataClass {
  public static final Genre goodGenre = Genre.builder().id(1L).name("genreName").build();
  public static final GenreDTO goodGenreDTO = GenreDTO.builder().id(1L).name("genreName").build();
  public static final Author goodAuthor = Author.builder().id(1L).name("authorName").build();
  public static final AuthorDTO goodAuthorDTO =
      AuthorDTO.builder().id(1L).name("authorName").build();
  public static final Book goodBook =
      Book.builder()
          .id(1L)
          .title("title")
          .year(2000)
          .authors(Collections.singletonList(goodAuthor))
          .genres(Collections.singletonList(goodGenre))
          .build();
  public static final BookDTO goodBookDTO =
      BookDTO.builder()
          .id(1L)
          .title("title")
          .year(2000)
          .genres(Collections.singletonList(goodGenreDTO))
          .authors(Collections.singletonList(goodAuthorDTO))
          .build();
  public static final BookFormDTO wrongGenreBookForm =
      BookFormDTO.builder()
          .title("title")
          .year(2000)
          .genres(Collections.singletonList("wrongGenre"))
          .build();
  public static final BookFormDTO wrongAuthorBookForm =
      BookFormDTO.builder()
          .title("title")
          .year(2000)
          .genres(Collections.singletonList("genreName"))
          .authors(Collections.singletonList("wrongAuthor"))
          .build();
  public static final BookFormDTO wrongTitleBookForm =
      BookFormDTO.builder()
          .title("wrongTitle")
          .year(2000)
          .genres(Collections.singletonList("genreName"))
          .authors(Collections.singletonList("authorName"))
          .build();
  public static final BookFormDTO goodBookForm =
      BookFormDTO.builder()
          .title("title")
          .year(2000)
          .genres(Collections.singletonList("genreName"))
          .authors(Collections.singletonList("authorName"))
          .build();
  public static final Book goodBookWithoutId =
      Book.builder()
          .title("title")
          .year(2000)
          .authors(Collections.singletonList(goodAuthor))
          .genres(Collections.singletonList(goodGenre))
          .build();
  public static final User goodUser =
          User.builder()
                  .id(1L)
                  .role(Role.USER)
                  .state(State.ACTIVE)
                  .username("username")
                  .hashPassword("qwe")
                  .build();
  public static final User goodUserWithoutId =
      User.builder()
          .role(Role.USER)
          .state(State.ACTIVE)
          .username("username")
          .hashPassword("qwe")
          .build();
  public static final UserFormDTO goodUserForm =
      UserFormDTO.builder().username("username").password("qwe").build();
  public static final UserFormDTO wrongUserForm =
      UserFormDTO.builder().username("wrongUsername").password("qwe").build();
  public static final UserDTO goodUserDTO =
      UserDTO.builder().id(1L).role("USER").state("ACTIVE").username("username").build();
  public static final Comment goodComment =
      Comment.builder()
          .id(1L)
          .book(goodBook)
          .text("text")
          .user(goodUser)
          .createDate(new Date(1))
          .modifyDate(new Date(1))
          .build();
  public static final CommentDTO goodCommentDTO =
      CommentDTO.builder()
          .id(1L)
          .book(goodBookDTO)
          .user(goodUserDTO)
          .text("text")
          .createDate("Date: 1970.01.01 Time: 03:00:00")
          .modifyDate("Date: 1970.01.01 Time: 03:00:00")
          .build();
  public static final CommentFormDTO goodCommentForm =
          CommentFormDTO.builder().bookId(1L).userId(1L).text("text").build();
  public static final Comment goodCommentWithoutId =
      Comment.builder()
          .book(goodBook)
          .text("text")
          .user(goodUser)
          .createDate(new Date(1))
          .modifyDate(new Date(1))
          .build();
}
