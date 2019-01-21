package com.netcracker.borodin.entity;

import lombok.*;

import javax.persistence.*;

@ToString(exclude = {"user", "book"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "m2m_user_book")
public class UserBook {

  @EmbeddedId private UserBookId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("bookId")
  private Book book;

  @Column(name = "rate")
  private int rate;
}
