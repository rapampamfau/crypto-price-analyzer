package org.cryptopriceanalyzer.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

public static Properties loadProperties() throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
          .getClassLoader()
          .getResourceAsStream("application.properties");
        configuration.load(inputStream);
    assert inputStream != null;
    inputStream.close();
        return configuration;
    }
}

