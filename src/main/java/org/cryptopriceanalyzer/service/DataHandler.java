package org.cryptopriceanalyzer.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DataHandler {

    public FileHandler fileHandler = new FileHandler();
    private static final String YAHOO_FINANCE = "https://finance.yahoo.com/quote/BTC-USD/history/?guccounter=1";
    public void downloadData(WebDriver driver) {
        fileHandler.deleteDuplicates();
        try {
            driver.get(YAHOO_FINANCE);

            WebElement rejectCookies = driver.findElement(By.name("reject"));
            rejectCookies.click();
            Thread.sleep(1000);

            WebElement here = driver.findElement(By.linkText("here"));
            here.click();

            WebElement downloadButton = driver.findElement(By.linkText("Download"));
            downloadButton.click();
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            System.out.println("Error:" + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
