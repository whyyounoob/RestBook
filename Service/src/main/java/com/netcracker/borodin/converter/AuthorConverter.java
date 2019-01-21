package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.entity.Author;

public class AuthorConverter {

    public static AuthorDTO converter(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

}
