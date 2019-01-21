package com.netcracker.borodin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class UserBookId implements Serializable {
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "book_id")
  private Long bookId;
}
