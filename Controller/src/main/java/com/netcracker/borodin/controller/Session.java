package com.netcracker.borodin.controller;

import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.entity.enums.Role;
import com.netcracker.borodin.entity.enums.State;
import com.netcracker.borodin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
class Session {

    private View view = new View();

    private UserService userService;
    private AuthorService authorService;
    private BookService bookService;
    private GenreService genreService;
    private CommentService commentService;

    private static UserDTO currentUser;

    @Autowired
    public Session(UserService userService, AuthorService authorService, BookService bookService, GenreService genreService, CommentService commentService) {
        this.userService = userService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    void testFunction() {
    }

    boolean signIn(Url url) {
        try {
            String username = url.getParams().get("username").get(0);
            String hashPassword = url.getParams().get("password").get(0);
            if (username.trim().equals("") || hashPassword.trim().equals("")) {
                System.out.println("Enter something other than spaces");
            } else {
                if (userService.signIn(username, hashPassword)) {
                    if (userService.getState().equals(State.ACTIVE)) {
                        currentUser = userService.getCurrentUser();
                        return true;
                    } else {
                        System.out.println("This account was deleted");
                        return false;
                    }
                }
            }

        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
            return false;
        }
        return false;
    }

    boolean signUp(Url url) {
        try {
            String username = url.getParams().get("username").get(0);
            String email = url.getParams().get("email").get(0);
            String hashPassword = url.getParams().get("password").get(0);
            if (username.trim().equals("") || email.trim().equals("")) {
                System.out.println("Enter something other than spaces");
                return false;
            }
            if (userService.checkUsername(username)) {
                System.out.println("This username already exist. Try other username.");
                return false;
            }
            if (userService.checkEmail(email)) {
                System.out.println("This username already exist. Try other username.");
                return false;
            }
            userService.signUp(username, hashPassword, email);
            if (signIn(url)) {
                System.out.println("Continue your work");
                return true;
            } else return false;
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
            return false;
        }
    }

    void signOut() {
        userService.signOut();
        currentUser = null;
        System.out.println("Now you guest");
    }

    void findBook(Url url) {
        view.showBooks(bookService.findBook(url.getParams()));
    }

    void addToDB(Url url) {
        try {
            if (userService.authorization().equals(Role.ADMIN)) {
                switch (url.getUrl()) {
                    case "book":
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", url.getParams().get("title").get(0));
                        map.put("year", Integer.parseInt(url.getParams().get("year").get(0)));
                        List<Integer> authorId = new ArrayList<>();
                        for (String string : url.getParams().get("author")) {
                            authorId.add(Integer.parseInt(string));
                        }
                        List<Integer> genreId = new ArrayList<>();
                        for (String string : url.getParams().get("genre")) {
                            genreId.add(Integer.parseInt(string));
                        }
                        map.put("authors", authorId);
                        map.put("genres", genreId);
                        if (bookService.addBook(map)) {
                            System.out.println("This book was add!");
                        } else {
                            System.out.println("Something went wrong when you add this book.");
                        }
                        break;
                    case "genre":
                        if (genreService.addGenre(url.getParams().get("name").get(0))) {
                            System.out.println("This genre was add!");
                        } else {
                            System.out.println("Something went wrong when you add this genre.");
                        }
                        break;
                    case "author":
                        if (authorService.addAuthor(url.getParams().get("name").get(0))) {
                            System.out.println("This author was add!");
                        } else {
                            System.out.println("Something went wrong when you add this author.");
                        }
                        break;
                    default:
                        System.out.println("Incorrect parameters!");
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
        }
    }

    void top100(Url url) {
        try {
            switch (url.getParams().get("key").get(0)) {
                case "null":
                    view.showBooks(bookService.showTop100(null, null));
                    break;
                case "genre":
                    view.showBooks(bookService.showTop100("genre", url.getParams().get("value").get(0)));
                    break;
                case "author":
                    view.showBooks(bookService.showTop100("author", url.getParams().get("value").get(0)));
                    break;
                default:
                    System.out.println("Incorrect parameters!");
            }
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
        }
    }

    void addMyBook(Url url) {
        userService.addMyBook(Long.parseLong(url.getParams().get("ID").get(0)),
                Integer.parseInt(url.getParams().get("rate").get(0)));
        System.out.println("Added!");
    }

    void showMyBook() {
        System.out.println("Your book: ");
        view.showMyBooks(userService.showMyBooks());
    }

    void findGenre(Url url) {
        try {
            view.showGenres(genreService.findGenre(url.getParams()));
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
        }
    }

    void findAuthor(Url url) {
        try {
            view.showAuthors(authorService.findAuthor(url.getParams()));
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
        }
    }

    void showUsers() {
        try {
            if (userService.authorization().equals(Role.ADMIN)) {

                view.showUsers(userService.getUsers());

            } else {
                System.out.println("You have not permission for this request!");
            }
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
        }
    }

    void changeUser(Url url) {
        try {
            if (userService.authorization().equals(Role.ADMIN)) {
                long id = Long.parseLong(url.getParams().get("ID").get(0));
                switch (url.getParams().get("key").get(0)) {
                    case "admin":
                        if (userService.makeAdmin(id)) {
                            System.out.println("Changed user is admin now.");
                        } else {
                            System.out.println("This user was admin already!");
                        }
                        break;
                    case "active":
                    case "delete":
                        if (userService.changeState(id, State.valueOf(url.getParams().get("key").get(0).toUpperCase()))) {
                            System.out.println("State changed!");
                        } else {
                            System.out.println("This user is already " + url.getParams().get("key").get(0));
                        }
                        break;
                    default:
                        System.out.println("Incorrect parameters!");
                }
            } else {
                System.out.println("You have not permission for this request!");
            }
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
        }
    }

    void updateMyBook(Url url) {
        userService.updateMyBook(Long.parseLong(url.getParams().get("ID").get(0)),
                Integer.parseInt(url.getParams().get("rate").get(0)));
        System.out.println("Updated!");
    }

    void workWithComments(Url url) {
        try {
            switch (url.getUrl()) {
                case "add":
                    commentService.addComment(FileUtils.read(new File("Controller/src/main/java/comment.txt")),
                            Long.parseLong(url.getParams().get("bookId").get(0)), currentUser.getId());
                    break;
                case "update":
                    commentService.updateComment(Long.parseLong(url.getParams().get("commentId").get(0)), FileUtils.read(new File("Controller/src/main/java/comment.txt")));
                    break;
                case "showBook":
                    view.showComment(commentService.showComment(Long.parseLong(url.getParams().get("bookId").get(0))));
                    break;
                case "delete":
                    if (!commentService.deleteComment(Long.parseLong(url.getParams().get("commentId").get(0)), currentUser.getId())) {
                        System.out.println("It is not your comment");
                    }
                    break;
                case "showUser":
                    view.showComment(commentService.showMyComment(currentUser.getId()));
                    break;
                default:
                    System.out.println("Incorrect parameters!");
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
        }
    }

    void updateMe(Url url) {
        try {
            String username = "";
            boolean isUsername = true;
            String email = "";
            boolean isEmail = true;
            Map<String, List<String>> map = url.getParams();
            if (map.containsKey("username")) {
                username = map.get("username").get(0);
                if (username.equals("")) {
                    username = currentUser.getUsername();
                } else if (currentUser.getUsername().equals(username)) {
                    System.out.println("Old username");
                } else if (!userService.checkUsername(username)) {
                    currentUser.setUsername(username);
                    isUsername = false;
                } else {
                    System.out.println("This username is already exist");
                }
            }
            if (map.containsKey("email")) {
                email = map.get("email").get(0);
                if (email.equals("")) {
                    email = currentUser.getEmail();
                } else if (currentUser.getEmail().equals(email)) {
                    System.out.println("Old email");
                } else if (!userService.checkEmail(email)) {
                    currentUser.setEmail(email);
                    isEmail = false;
                } else {
                    System.out.println("This email is already exist");
                }
            }
            userService.updateCurrentUser(currentUser);
            currentUser = userService.getCurrentUser();
            if (!isEmail) {
                System.out.println("Email was change");
            }
            if (!isUsername) {
                System.out.println("Username was change");
            }
        } catch (NullPointerException e) {
            System.out.println("Not enough parameters");
        }

    }
}
