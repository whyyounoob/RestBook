package com.netcracker.borodin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBookFormDTO {
  private int rate;
  private int bookId;
  private int userId;
}
