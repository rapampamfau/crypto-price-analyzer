package org.cryptopriceanalyzer.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverConfig {

    private static final String WEB_KEY = "webdriver.chrome.driver";


    private static String getDriverPath(String os) throws Exception {
        String path;
        if (os.contains("win")) {
            path = "src/main/resources/chromedriver-win64/chromedriver.exe";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            path = "src/main/resources/chromedriver-linux64/chromedriver";
        } else if (os.contains("mac")) {
            path = "src/main/resources/chromedriver-mac-x64/chromedriver";
        } else {
            throw new Exception("Your OS is not supported!");
        }
        return path;
    }

    private static void setProperty() {
        String os = System.getProperty("os.name").toLowerCase();
        String driverPath;
        try {
            driverPath = getDriverPath(os);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.setProperty(WEB_KEY, driverPath);
    }

    public static WebDriver getDriver() {
        setProperty();
        return new ChromeDriver();
    }
}
