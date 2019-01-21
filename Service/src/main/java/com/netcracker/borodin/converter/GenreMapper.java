package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface GenreMapper {
  @Mappings({
    @Mapping(target = "id", source = "entity.id"),
    @Mapping(target = "name", source = "entity.name")
  })
  GenreDTO genreToGenreDTO(Genre entity);

  @Mappings({
    @Mapping(target = "id", source = "dto.id"),
    @Mapping(target = "name", source = "dto.name")
  })
  Genre genreDTOToGenre(GenreDTO dto);

  List<GenreDTO> listGenreToListGenreDTO(List<Genre> list);
}
