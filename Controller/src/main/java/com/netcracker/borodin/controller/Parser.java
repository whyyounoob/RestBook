package com.netcracker.borodin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Parser {

    @Autowired
    private Session session;
    static boolean isWork = false;

    public void parser(String url) {

        List<Url> urls = parseUrl(url);
        switch (urls.get(0).getUrl()) {
            case "signIn":
                if (session.signIn(urls.get(0))) {
                    System.out.println("Continue your work");
                    isWork = true;
                } else {
                    System.out.println("Log in or work as guest.");
                }
                break;
            case "signUp":
                isWork = session.signUp(urls.get(0));
                break;
            case "signOut":
                session.signOut();
                isWork = false;
                break;
            case "top100":
                session.top100(urls.get(0));
                break;
            case "myBook":
                if (isWork) {
                    if (urls.get(1).getUrl().equals("all")) {
                        session.showMyBook();
                    } else if (urls.get(1).getUrl().equals("book")) {
                        if (urls.get(2).getUrl().equals("add")) {
                            session.addMyBook(urls.get(2));
                        } else if (urls.get(2).getUrl().equals("update")) {
                            session.updateMyBook(urls.get(2));
                        }
                    }
                    break;
                } else {
                    System.out.println("First signIn or signUp!");
                    break;
                }

            case "find":
                if (isWork) {
                    if (urls.get(1).getUrl().equals("book")) {
                        session.findBook(urls.get(1));
                    } else if (urls.get(1).getUrl().equals("genre")) {
                        session.findGenre(urls.get(1));
                    } else if (urls.get(1).getUrl().equals("author")) {
                        session.findAuthor(urls.get(1));
                    }
                } else {
                    System.out.println("First signIn or signUp!");
                    break;
                }
                break;
            case "users":
                if (isWork) {
                    session.showUsers();
                } else {
                    System.out.println("First signIn or signUp!");
                    break;
                }
                break;
            case "changeUser":
                if (isWork) {
                    if (urls.get(0).getParams().get("who").get(0).equals("me")) {
                        session.updateMe(urls.get(0));
                    } else if (urls.get(0).getParams().get("who").get(0).equals("other")) {
                        session.changeUser(urls.get(0));
                    }
                } else {
                    System.out.println("First signIn or signUp!");
                    break;
                }
                break;
            case "add":
                if (isWork) {
                    session.addToDB(urls.get(1));
                } else {
                    System.out.println("First signIn or signUp!");
                    break;
                }
                break;
            case "comment":
                if (isWork) {
                    session.workWithComments(urls.get(1));
                } else {
                    System.out.println("First signIn or signUp!");
                    break;
                }
                break;
            case "test":
                session.testFunction();
                break;
            default:
                System.out.println("Incorrect URL. Try again.");
        }
    }

    public List<Url> parseUrl(String url) {
        List<Url> list = new ArrayList<>();
        String[] urls = url.split("/");
        for (String string : urls) {
            if (string.contains("signOut")) {
                List<Url> l = new ArrayList<>();
                l.add(Url.builder().url("signOut").build());
                return l;
            } else if (string.contains("exit")) {
                List<Url> l = new ArrayList<>();
                l.add(Url.builder().url("exit").build());
                return l;
            }

            Url test = new Url();
            if (string.contains("?")) {
                String name = string.substring(0, string.lastIndexOf("?"));
                String p = string.substring(string.lastIndexOf("?") + 1);
                String[] params = p.split("&");
                Map<String, List<String>> map = new HashMap<>();
                for (String str : params) {
                    String key = str.substring(0, str.lastIndexOf("="));
                    String value;
                    value = str.substring(str.lastIndexOf("=") + 1);
                    List<String> values = new ArrayList<>(Arrays.asList(value.split("\\+")));

                    map.put(key, values);
                }
                test.setParams(map);
                test.setUrl(name);
            } else {
                test.setUrl(string);
            }


            list.add(test);
        }
        return list;
    }
}
