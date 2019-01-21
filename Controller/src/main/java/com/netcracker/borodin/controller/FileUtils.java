package com.netcracker.borodin.controller;

import java.io.*;

public class FileUtils {

    public static String read(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public static void write(File file, String write) throws FileNotFoundException {

        exists(file);

        try {
            try (PrintWriter out = new PrintWriter(file.getAbsoluteFile())) {
                out.print(write);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void exists(File file) throws FileNotFoundException {

        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
    }


}
