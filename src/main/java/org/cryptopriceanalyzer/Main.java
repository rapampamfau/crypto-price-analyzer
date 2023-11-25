package org.cryptopriceanalyzer;

import org.cryptopriceanalyzer.config.WebDriverConfig;
import org.openqa.selenium.WebDriver;

public class Main {
    public static void main(String[] args) {

        WebDriver wd = WebDriverConfig.getDriver();
        wd.get("https://www.google.com/");
    }
}