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
public class BookFormDTO {
  private String title;
  private int year;
  private List<String> genres;
  private List<String> authors;
}
