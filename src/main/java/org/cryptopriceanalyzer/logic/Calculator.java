package org.cryptopriceanalyzer.logic;

import java.util.List;

public class Calculator {

    public static double calculateSMA(List<Double> values, int period) {
        double sum = 0;
        for (int i = 0; i < period; i++) {
            sum += values.get(i);
        }
        return sum / period;
    }

    public static double calculateEMA(List<Double> values, int period) {
        double k = 2.0 / (period + 1.0);
        double ema = values.get(0);
        for (int i = 1; i < period; i++) {
            ema = values.get(i) * k + ema * (1 - k);
        }
        return ema;
    }
}
