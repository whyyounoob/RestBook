package com.netcracker.borodin.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private long id;
    private String text;
    private String createDate;
    private String modifyDate;
    private String user;
    private String book;
}
