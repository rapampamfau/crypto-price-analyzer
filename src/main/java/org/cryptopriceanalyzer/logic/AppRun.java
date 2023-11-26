package org.cryptopriceanalyzer.logic;

import org.cryptopriceanalyzer.config.PropertiesLoader;
import org.cryptopriceanalyzer.config.WebDriverConfig;
import org.cryptopriceanalyzer.service.CsvParser;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import com.opencsv.exceptions.CsvException;
import org.cryptopriceanalyzer.service.DataHandler;
import org.cryptopriceanalyzer.service.FileHandler;
import org.cryptopriceanalyzer.service.EmailSender;
import java.util.Properties;

public class AppRun {

    static Properties conf;

    static {
        try {
            conf = PropertiesLoader.loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String user = conf.getProperty("app.user");
    static DataHandler dataHandler;

    static {
        try {
            dataHandler = new DataHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String email = conf.getProperty("email.recipient");
    static Properties properties = new Properties();

    static String downloadDir = FileHandler.getDownloadDir(user);
    static String os = System.getProperty("os.name").toLowerCase();

    public static void run() throws IOException {
        properties.load(AppRun.class.getClassLoader().getResourceAsStream("application.properties"));
        dataHandler.downloadData(WebDriverConfig.getDriver());
        try {
            // Step 1: Parse CSV file
            List<String[]> csvData;
            if (os.contains("win")) {
                csvData = CsvParser.parseCsvFile(downloadDir + "\\BTC-USD.csv");
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix") || os.contains("mac")) {
                csvData = CsvParser.parseCsvFile(downloadDir + "/BTC-USD.csv");
            } else {
                throw new UnsupportedOperationException("Your OS is not supported!!");
            }

            // Extract the Close column, handling exceptions and filtering out invalid values
            List<Double> closeColumn = csvData.stream()
                    .skip(1) // Skip header
                    .map(data -> parseDoubleWithDefault(data[4])) // Assuming Close column is at index 4
                    .filter(value -> !Double.isNaN(value)) // Filter out invalid values
                    .collect(Collectors.toList());

            // Step 2: Calculate SMA and EMA
            int period = 8;
            double sma = Calculator.calculateSMA(closeColumn, period);
            double ema = Calculator.calculateEMA(closeColumn, period);

            System.out.println("SMA: " + sma);
            System.out.println("EMA: " + ema);

            // Step 3: Send email with the results
            String emailBody = "SMA: " + sma + "\nEMA: " + ema;
            EmailSender.sendEmail(email, "BTC-USD", emailBody);

        } catch (IOException | CsvException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }
    private static double parseDoubleWithDefault(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            return Double.NaN;
        }
    }
}
