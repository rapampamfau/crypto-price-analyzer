package org.cryptopriceanalyzer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileHandler {

    private static final String os = System.getProperty("os.name").toLowerCase();

    private final String user = getUser();

    public void deleteDuplicates() {
        String downloadsDirectory = getDownloadDir(user);
        String filePattern = "BTC-USD(\\d*).csv";

        try (Stream<Path> paths = Files.walk(Paths.get(downloadsDirectory))) {
            paths
                .filter(Files::isRegularFile)
                .filter(path -> Pattern.matches(filePattern, path.getFileName().toString()))
                .forEach(FileHandler::deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteFile(Path path) {
        try {
            Files.delete(path);
            System.out.println("File deleted: " + path);
        } catch (Exception e) {
            System.err.println("Error deleting file: " + path + ", " + e.getMessage());
        }
    }

    static String getFirstLineFromResource(String fileName) {
        ClassLoader classLoader = FileHandler.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found! " + fileName);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                return reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    static String getDownloadDir(String user) {
        if (os.contains("win")) {
            return "C:\\" + user + "\\Downloads";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix") || os.contains("mac")) {
            return ("/home/" + user) + "/Downloads";
        } else {
            throw new UnsupportedOperationException("Your OS is not supported!!");
        }
    }

    private static String getUser() {
        String fileName = "users.txt";
        String firstLine = getFirstLineFromResource(fileName);

        if (firstLine != null) {
            System.out.println("First Line: " + firstLine);
        } else {
            System.out.println("Unable to read the first line.");
        }
        return firstLine;
    }
}
