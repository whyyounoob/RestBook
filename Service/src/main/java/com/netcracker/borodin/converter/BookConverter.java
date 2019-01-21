package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Genre;

public class BookConverter {

    public static BookDTO converter(Book book) {
        StringBuilder genres = new StringBuilder();
        StringBuilder authors = new StringBuilder();
        for (Author author : book.getAuthors()) {
            authors.append(author.getName());
            authors.append(", ");
        }
        for (Genre genre : book.getGenres()) {
            genres.append(genre.getName());
            genres.append(", ");
        }
        authors.deleteCharAt(authors.lastIndexOf(","));
        genres.deleteCharAt(genres.lastIndexOf(" "));
        genres.deleteCharAt(genres.lastIndexOf(","));


        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .averageRate(book.getAverageRate())
                .year(book.getYear())
                .authors(authors.toString())
                .genres(genres.toString())
                .build();
    }
}
