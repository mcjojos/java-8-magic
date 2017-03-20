package com.jojos.home.files;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * todo: create javadoc
 * <p>
 * Created by karanikasg@gmail.com.
 */
public class File4Fun {

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

    /**
     * Special thanks to Greg Briggs as seen under
     * @see <a href="http://www.uofr.net/~greg/java/get-resource-listing.html">Greg's source code</a>
     *
     * List directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * Works for regular files and also JARs.
     *
     * @param clazz Any java class that lives in the same place as the resources you want.
     * @param path Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException if this URL is not formatted strictly according to to RFC2396 and cannot be converted to a URI.
     * @throws IOException If character encoding needs to be consulted, but named character encoding is not supported
     */
    public String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);

        if (Objects.nonNull(dirURL) && dirURL.getProtocol().equals("file")) {
        /* A file path: easy enough */
            return new java.io.File(dirURL.toURI()).list();
        }

        if (Objects.isNull(dirURL)) {
        /*
         * In case of a jar file, we can't actually find a directory.
         * Have to assume the same jar as clazz.
         */
            String me = clazz.getName().replace(".", "/")+".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (Objects.nonNull(dirURL) && dirURL.getProtocol().equals("jar")) {
        /* A JAR path */
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            Set<String> result = new HashSet<>(); //avoid duplicates in case it is a subdirectory
            while(entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(path)) { //filter according to the path
                    String entry = name.substring(path.length());
                    int checkSubdir = entry.indexOf("/");
                    if (checkSubdir >= 0) {
                        // if it is a subdirectory, we just return the directory name
                        entry = entry.substring(0, checkSubdir);
                    }
                    result.add(entry);
                }
            }
            return result.toArray(new String[result.size()]);
        }

        throw new UnsupportedOperationException("Cannot list files for URL "+dirURL);
    }
}
