package com.netcracker.borodin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
  private Double averageRate;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "m2m_book_genre",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "genre_id")})
  private List<Genre> genres;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "m2m_book_author",
      joinColumns = {@JoinColumn(name = "book_id")},
      inverseJoinColumns = {@JoinColumn(name = "author_id")})
  private List<Author> authors;

  @OneToMany(mappedBy = "book", orphanRemoval = true)
  private List<UserBook> users;
}
