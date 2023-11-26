package org.cryptopriceanalyzer;

import com.opencsv.exceptions.CsvException;
import org.cryptopriceanalyzer.logic.AppRun;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, CsvException {
        AppRun.run();
    }
}
