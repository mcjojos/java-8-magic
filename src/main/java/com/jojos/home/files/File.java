package com.jojos.home.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * todo: create javadoc
 * <p>
 * Created by karanikasg@gmail.com.
 */
public class File {

    public static void demonstrate() {
        Stream<String> stream = null;

        try {
            printCurrentDirectory();
            stream = Files.lines(Paths.get("src/main/resources/file.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("============= demo lines parsing");
        stream.forEach(System.out::println);
    }

    private static void printCurrentDirectory() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
    }
}
