package com.messaging.util;

import com.messaging.exception.ExceptionHandler;

import java.io.InputStream;
import java.util.Properties;

/**
 * Utility to read configuration properties.
 * 
 * @author Rahul Shukla
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    // Code below runs once when the class is first loaded
    static {
        try(InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")){
            if(inputStream != null){
                properties.load(inputStream);
            } else {
                Logger.error(" ERROR IN CONFIG READER ::  ", "config.properties not found in classpath");
            }
        } catch (Exception exception){
            ExceptionHandler.handle(ConfigReader.class.getSimpleName(), exception);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException| NullPointerException exception) {
            Logger.error(" EXCEPTION IN CONFIG READER :: " , " Invalid or Missing Value for Key " + key + ", using default :: " + defaultValue);
            return defaultValue;
        }
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException| NullPointerException exception) {
            Logger.error(" EXCEPTION IN CONFIG READER :: ", " Invalid or Missing Value for Key " + key + ", using default :: " + defaultValue);
            return defaultValue;
        }
    }
}
