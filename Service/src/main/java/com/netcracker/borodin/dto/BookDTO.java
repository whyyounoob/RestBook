package com.netcracker.borodin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    private String title;

    private int year;

    private Double averageRate;

    private List<AuthorDTO> authors;

    private List<GenreDTO> genres;

}
