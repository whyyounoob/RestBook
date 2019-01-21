package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AuthorMapper {

  @Mappings({
    @Mapping(target = "id", source = "entity.id"),
    @Mapping(target = "name", source = "entity.name")
  })
  AuthorDTO authorToAuthorDTO(Author entity);

  @Mappings({
    @Mapping(target = "id", source = "dto.id"),
    @Mapping(target = "name", source = "dto.name")
  })
  Author authorDTOToAuthor(AuthorDTO dto);

  List<AuthorDTO> authorListToAuthorDTOList(List<Author> authors);
}
