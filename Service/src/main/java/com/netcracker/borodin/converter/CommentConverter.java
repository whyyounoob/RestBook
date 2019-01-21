package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.CommentDTO;
import com.netcracker.borodin.entity.Comment;

import java.text.SimpleDateFormat;

public class CommentConverter {
    static SimpleDateFormat formatDate = new SimpleDateFormat("'Date:' yyyy.MM.dd  'Time:' HH:mm:ss");

    public static CommentDTO convert(Comment comment) {
        return CommentDTO.builder().id(comment.getId())
                .text(comment.getText())
                .user("Id: " + comment.getUser().getId() + " " + comment.getUser().getUsername())
                .book("Id : " + comment.getBook().getId() + " " + comment.getBook().getTitle())
                .createDate(formatDate.format(comment.getCreateDate()))
                .modifyDate(formatDate.format(comment.getModifyDate()))
                .build();

    }
}
