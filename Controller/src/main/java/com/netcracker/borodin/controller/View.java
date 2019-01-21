package com.netcracker.borodin.controller;

import com.netcracker.borodin.dto.*;

import java.util.List;

public class View {

    public void showBooks(List<BookDTO> bookList) {
        for (BookDTO book : bookList) {
            showBook(book);
            System.out.println("\n");
        }
    }

    public void showUsers(List<UserDTO> userList) {
        for (UserDTO user : userList) {
            System.out.println("\tID: " + user.getId());
            System.out.println("\tUsername: " + user.getUsername());
            System.out.println("\tName: " + user.getName());
            System.out.println("\tSurname: " + user.getSurname());
            System.out.println("\tEmail: " + user.getEmail());
            System.out.println("\tState: " + user.getState());
            System.out.println("\tRole: " + user.getRole());
            System.out.println("\n");
        }
    }

    public void showGenres(List<GenreDTO> genreList) {
        for (GenreDTO genre : genreList) {
            System.out.println("\n\tID: " + genre.getId());
            System.out.println("\tName: " + genre.getName());
            System.out.println("\n");
        }
    }

    public void showAuthors(List<AuthorDTO> authorList) {
        for (AuthorDTO author : authorList) {
            System.out.println("\n\tID: " + author.getId());
            System.out.println("\tAuthor: " + author.getName());
            System.out.println("\n");
        }
    }

    public void showComment(List<CommentDTO> commentList) {
        for (CommentDTO comment : commentList) {
            System.out.println("\n\tID comment: " + comment.getId());
            System.out.println("\t\t" + comment.getText());
            System.out.println("\tWrite by(: " + comment.getUser());
            System.out.println("\tWrite for: " + comment.getBook());
            System.out.println("\tCreate: " + comment.getCreateDate());
            System.out.println("\tModify: " + comment.getModifyDate());
            System.out.println("\n");
        }
    }

    private void showBook(BookDTO book){
        System.out.println("\n\tID: " + book.getId());
        System.out.println("\tTitle: " + book.getTitle());
        System.out.println("\tYear: " + book.getYear());
        System.out.println("\tAuthors: " + book.getAuthors());
        System.out.println("\tGenres: " + book.getGenres());
        if (book.getAverageRate() != -1) {
            System.out.println("\tAverage rate: " + book.getAverageRate());
        } else {
            System.out.println("\tThis book without rate.");
        }
    }

    public void showMyBooks(List<BookDTO> bookList) {
        for (BookDTO book : bookList) {
            showBook(book);
            if (book.getRateByCurrentUser() >= 0) {
                System.out.println("\tYour rate: " + book.getRateByCurrentUser());
            }
            System.out.println("\n");
        }
    }
}
