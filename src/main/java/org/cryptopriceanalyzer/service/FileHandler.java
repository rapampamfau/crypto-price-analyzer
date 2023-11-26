package org.cryptopriceanalyzer.service;

import org.cryptopriceanalyzer.config.PropertiesLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileHandler {

    Properties conf = PropertiesLoader.loadProperties();
    String user = conf.getProperty("app.user");
    private static final String os = System.getProperty("os.name").toLowerCase();

    public FileHandler() throws IOException {
    }

    public void deleteDuplicates() {
        //String user = getUser();
        String downloadsDirectory = getDownloadDir(user);
        String filePattern = "BTC-USD(\\d*).csv";

        try (Stream<Path> paths = Files.walk(Paths.get(downloadsDirectory))) {
            paths
                .filter(Files::isRegularFile)
                .filter(path -> Pattern.matches(filePattern, path.getFileName().toString()))
                .forEach(FileHandler::deleteFile);
        } catch (Exception e) {
            System.err.println("Error deleting files: " + e.getMessage());
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

    public static String getDownloadDir(String user) {
        if (os.contains("win")) {
            return "C:\\" + user + "\\Downloads";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix") || os.contains("mac")) {
            return ("/home/" + user) + "/Downloads";
        } else {
            throw new UnsupportedOperationException("Your OS is not supported!!");
        }
    }
}
