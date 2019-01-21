package com.netcracker.borodin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comment {
  @Id
  @Column(name = "comment_id", unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "text")
  private String text;

  @Column(name = "create_date")
  private Date createDate;

  @Column(name = "modify_date")
  private Date modifyDate;

  @ManyToOne private Book book;

  @ManyToOne private User user;
}
