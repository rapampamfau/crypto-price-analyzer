package org.cryptopriceanalyzer.service;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

        @Test
        void deleteDuplicates() {
            // Arrange
            String downloadsDirectory = "src/test/resources";
            String filePattern = "BTC-USD(\\d*).csv";
            // Act
            try (Stream<Path> paths = Files.walk(Paths.get(downloadsDirectory))) {
                paths
                        .filter(Files::isRegularFile)
                        .filter(path -> Pattern.matches(filePattern, path.getFileName().toString()))
                        .forEach(FileHandler::deleteFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Assert
            Path path = Paths.get("src/test/resources/BTC-USD.csv");
            assertFalse(Files.exists(path));
        }

        @Test
        void getFirstLineFromResource() {
            // Arrange
            String user = "test_users.txt";

            // Act
            String result = FileHandler.getFirstLineFromResource(user);

            // Assert
            assertEquals("michal", result);

        }

        @Test
        void getDownloadDir() {
            // Arrange
            String user = "michal";
            String expected = "michal.home/Downloads";

            // Act
            String result = FileHandler.getDownloadDir(user);

            // Assert
            assertEquals(expected, result);
        }
}