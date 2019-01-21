package com.netcracker.borodin.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class UserConsole {

    private InputStream inputStream = System.in;
    private Reader inputStreamReader = new InputStreamReader(inputStream);
    private BufferedReader scanner = new BufferedReader(inputStreamReader);
    private String url = "";
    @Autowired
    private Parser parser;

    public void console() {
        try {
            while (true) {
                int command;
                System.out.println("What you want to do?");
                System.out.println("1 - Sign in");
                System.out.println("2 - Sign up");
                System.out.println("3 - Sign out");
                System.out.println("4 - Show top100");
                System.out.println("5 - Show top100 by genre");
                System.out.println("6 - Show top100 by author");
                System.out.println("7 - Show my books");
                System.out.println("8 - Add book to my list");
                System.out.println("9 - Change rate for book");
                System.out.println("10 - Find book");
                System.out.println("11 - Find genre");
                System.out.println("12 - Find author");
                System.out.println("13 - Show users");
                System.out.println("14 - Update info about yourself");
                System.out.println("15 - Change state(from delete to active/from active to delete");
                System.out.println("16 - Up to admin");
                System.out.println("17 - Add genre to DB");
                System.out.println("18 - Add author to DB");
                System.out.println("19 - Add book to DB");
                System.out.println("20 - Add comment to book");
                System.out.println("21 - Modify comment");
                System.out.println("22 - Delete comment");
                System.out.println("23 - Show comment to book");
                System.out.println("24 - Show your comment");
                System.out.println("0 - Exit");
                try {
                    command = Integer.parseInt(scanner.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect input! Try again.");
                    continue;
                }
                switch (command) {
                    case 1:
                        System.out.println("Enter your username: ");
                        String username = scanner.readLine();
                        System.out.println("Enter your password: ");
                        String password = DigestUtils.md5Hex(scanner.readLine());
                        url += "signIn?username=" + username + "&password=" + password;
                        parser.parser(url);
                        url = "";
                        break;
                    case 2:
                        System.out.println("Enter your username: ");
                        String newUsername = scanner.readLine();
                        System.out.println("Enter your email: ");
                        String email = scanner.readLine();
                        System.out.println("Enter your password: ");
                        String newPassword = DigestUtils.md5Hex(scanner.readLine());
                        url += "signUp?username=" + newUsername + "&email=" +
                                email + "&password=" + newPassword;
                        parser.parser(url);
                        url = "";
                        break;
                    case 3:
                        url += "signOut";
                        parser.parser(url);
                        url = "";
                        break;
                    case 4:
                        url = "top100?key=null";
                        parser.parser(url);
                        url = "";
                        break;
                    case 5:
                        System.out.println("Enter ID genre: ");
                        String genreID = scanner.readLine();
                        url += "top100?key=genre&value=" + genreID;
                        parser.parser(url);
                        url = "";
                        break;
                    case 6:
                        System.out.println("Enter ID author: ");
                        String authorUD = scanner.readLine();
                        url += "top100?key=author&value=" + authorUD;
                        parser.parser(url);
                        url = "";
                        break;
                    case 7:
                        url += "myBook/all";
                        parser.parser(url);
                        url = "";
                        break;
                    case 8:
                        System.out.println("Enter ID book: ");
                        String bookIdAdd = scanner.readLine();
                        System.out.println("Enter rate: ");
                        int rateAdd;
                        while (true) {
                            try {
                                rateAdd = Integer.parseInt(scanner.readLine());
                                if (rateAdd >= 0 && rateAdd <= 10) {
                                    break;
                                } else {
                                    System.out.println("Incorrect input! Try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Incorrect input! Try again.");
                            }
                        }
                        url += "myBook/book/add?ID=" + bookIdAdd + "&rate=" + rateAdd;
                        parser.parser(url);
                        url = "";
                        break;
                    case 9:
                        System.out.println("Enter ID book: ");
                        String bookID = scanner.readLine();
                        System.out.println("Enter rate: ");
                        int rate;
                        while (true) {
                            try {
                                rate = Integer.parseInt(scanner.readLine());
                                if (rate >= 0 && rate <= 10) {
                                    break;
                                } else {
                                    System.out.println("Incorrect input! Try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Incorrect input! Try again.");
                            }
                        }
                        url += "myBook/book/update?ID=" + bookID + "&rate=" + rate;
                        parser.parser(url);
                        url = "";
                        break;
                    case 10:
                        System.out.println("Find book by genre id(1), author id(2), title(3) or book id(4)");
                        url += "find/book?";
                        int num;
                        try {
                            num = Integer.parseInt(scanner.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect input! Try again.");
                            continue;
                        }
                        switch (num) {
                            case 1:
                                System.out.println("Enter genre ID: ");
                                String IDgenre = scanner.readLine();
                                url += "genre=" + IDgenre;
                                break;
                            case 2:
                                System.out.println("Enter author ID: ");
                                String IDauthor = scanner.readLine();
                                url += "author=" + IDauthor;
                                break;
                            case 3:
                                System.out.println("Enter book title: ");
                                String title = scanner.readLine();
                                url += "title=" + title;
                                break;
                            case 4:
                                System.out.println("Enter book ID: ");
                                String IDbook = scanner.readLine();
                                url += "ID=" + IDbook;
                                break;
                            default:
                                System.out.println("Incorrect input! Try again!");
                                break;
                        }
                        parser.parser(url);
                        url = "";
                        break;
                    case 11:
                        System.out.println("Find genre by ID(1) or name(2).");
                        url += "find/genre?";
                        int num11;
                        try {
                            num11 = Integer.parseInt(scanner.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect input! Try again.");
                            continue;
                        }
                        switch (num11) {
                            case 1:
                                System.out.println("Enter genre ID: ");
                                String gID = scanner.readLine();
                                url += "ID=" + gID;
                                break;
                            case 2:
                                System.out.println("Enter name: ");
                                String genreName = scanner.readLine();
                                url += "name=" + genreName;
                                break;
                            default:
                                System.out.println("Incorrect input! Try again!");
                                break;
                        }
                        parser.parser(url);
                        url = "";
                        break;
                    case 12:
                        System.out.println("Find author by ID(1) or name(2).");
                        url += "find/author?";
                        int num12;
                        try {
                            num12 = Integer.parseInt(scanner.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect input! Try again.");
                            continue;
                        }
                        switch (num12) {
                            case 1:
                                System.out.println("Enter autnor ID: ");
                                String aID = scanner.readLine();
                                url += "ID=" + aID;
                                break;
                            case 2:
                                System.out.println("Enter name: ");
                                String autnorName = scanner.readLine();
                                url += "name=" + autnorName;
                                break;
                            default:
                                System.out.println("Incorrect input! Try again!");
                                break;
                        }
                        parser.parser(url);
                        url = "";
                        break;
                    case 13:
                        url += "users";
                        parser.parser(url);
                        url = "";
                        break;
                    case 14:
                        System.out.println("Enter new username or press enter to skip this: ");
                        String updateUsername = scanner.readLine();
                        System.out.println("Enter new email or press enter to skip this: ");
                        String updateEmail = scanner.readLine();
                        url += "changeUser?who=me&username=" + updateUsername.trim() + "&email=" + updateEmail.trim();
                        parser.parser(url);
                        url = "";
                        break;
                    case 15:
                        System.out.println("Enter the user ID: ");
                        String userId = scanner.readLine();
                        url += "changeUser?who=other&ID=" + userId + "&key=";
                        System.out.println("1 - Make active, 2 - Delete user");
                        String choose = scanner.readLine();
                        switch (choose) {
                            case "1":
                                url += "active";
                                break;
                            case "2":
                                url += "delete";
                                break;
                            default:
                                System.out.println("Incorrect input! Try again!");
                                break;
                        }
                        parser.parser(url);
                        url = "";
                        break;
                    case 16:
                        System.out.println("Enter the user ID: ");
                        String userID = scanner.readLine();
                        url += "changeUser?who=other&ID=" + userID + "&key=admin";
                        parser.parser(url);
                        url = "";
                        break;
                    case 17:
                        System.out.println("Enter the name of genre: ");
                        String gn = scanner.readLine();
                        url += "add/genre?name=" + gn;
                        parser.parser(url);
                        url = "";
                        break;
                    case 18:
                        System.out.println("Enter the name of author: ");
                        String an = scanner.readLine();
                        url += "add/author?name=" + an;
                        parser.parser(url);
                        url = "";
                        break;
                    case 19:
                        System.out.println("Enter title of the book: ");
                        String title = scanner.readLine();
                        System.out.println("Enter the year of the book: ");
                        String year = scanner.readLine();
                        System.out.println("Enter authors ID(connect with +): ");
                        String authors = scanner.readLine();
                        System.out.println("Enter genres ID(connect with +): ");
                        String genres = scanner.readLine();
                        url += "add/book?title=" + title + "&year=" + year + "&author=" + authors + "&genre=" + genres;
                        parser.parser(url);
                        url = "";
                        break;
                    case 20:
                        System.out.println("Enter ID of the book: ");
                        String bookId = scanner.readLine();
                        System.out.println("Enter your comment: ");
                        String comment = scanner.readLine();
                        FileUtils.write(new File("Controller/src/main/java/comment.txt"), comment);
                        url += "comment/add?bookId=" + bookId;
                        parser.parser(url);
                        url = "";
                        break;
                    case 21:
                        System.out.println("Enter ID of the comment: ");
                        String commentId = scanner.readLine();
                        System.out.println("Enter your comment: ");
                        String updateComment = scanner.readLine();
                        FileUtils.write(new File("Controller/src/main/java/comment.txt"), updateComment);
                        url += "comment/update?commentId=" + commentId;
                        parser.parser(url);
                        url = "";
                        break;
                    case 22:
                        System.out.println("Enter ID of the comment: ");
                        String idComment = scanner.readLine();
                        url += "comment/delete?commentId=" + idComment;
                        parser.parser(url);
                        url = "";
                        break;
                    case 23:
                        System.out.println("Enter ID of the book: ");
                        String idBook = scanner.readLine();
                        url += "comment/showBook?bookId=" + idBook;
                        parser.parser(url);
                        url = "";
                        break;
                    case 24:
                        url += "comment/showUser";
                        parser.parser(url);
                        url = "";
                        break;
                    case 99:
                        url += "test";
                        parser.parser(url);
                        url = "";
                        break;
                    case 0:
                        System.out.println("Bye!");
                        return;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
