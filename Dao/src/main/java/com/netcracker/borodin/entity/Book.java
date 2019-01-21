package com.netcracker.borodin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "year")
    private int year;

    @Column(name = "average_rate")
    private double averageRate = -1.0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "m2m_book_genre",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "m2m_book_author",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "books")
    private Set<User> users = new HashSet<>();

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

}
