package com.netcracker.borodin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String text;
    private String createDate;
    private String modifyDate;
    private UserDTO user;
    private BookDTO book;
}
