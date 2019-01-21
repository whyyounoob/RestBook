package com.netcracker.borodin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDTO {

    private long id;

    private String title;

    private int year;

    private double averageRate;

    private String authors;

    private String genres;

    private int rateByCurrentUser = -1;

}
