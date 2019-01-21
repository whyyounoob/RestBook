package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.entity.Genre;

public class GenreConverter {

    public static GenreDTO converter(Genre genre) {
        return GenreDTO.builder().id(genre.getId()).name(genre.getName()).build();
    }
}
