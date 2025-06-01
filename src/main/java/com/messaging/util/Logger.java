package com.messaging.util;

import com.messaging.enums.LogLevel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.messaging.util.Constants.GLOBAL_LOG_LEVEL;

/**
 * Centralized Logger with level filtering based on config properties.
 *
 * Set `log.level=INFO` in config.properties to control minimum level.
 *
 * Supported levels: TRACE < DEBUG < INFO < WARN < ERROR
 *
 * Author: Rahul Shukla
 */
public class Logger {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Configured log level from properties
    private static final LogLevel CONFIGURED_LEVEL;

    static {
        String levelFromConfig = ConfigReader.getProperty(GLOBAL_LOG_LEVEL);
        CONFIGURED_LEVEL = LogLevel.valueOf(levelFromConfig.toUpperCase());
    }

    private static boolean shouldLog(LogLevel level) {
        return level.ordinal() >= CONFIGURED_LEVEL.ordinal();
    }

    public static void log(LogLevel level, String who, String message) {
        if (!shouldLog(level)) return;

        String time = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String threadName = Thread.currentThread().getName();
        System.out.printf("[%s] [%s] [%s] [%s] %s%n", time, threadName, level, who, message);
    }

    public static void info(String who, String message) {
        log(LogLevel.INFO, who, message);
    }

    public static void debug(String who, String message) {
        log(LogLevel.DEBUG, who, message);
    }

    public static void error(String who, String message) {
        log(LogLevel.ERROR, who, message);
    }

    public static void warn(String who, String message) {
        log(LogLevel.WARN, who, message);
    }

    public static void trace(String who, String message) {
        log(LogLevel.TRACE, who, message);
    }

    public static void log(String message) {
        if (!shouldLog(LogLevel.INFO)) return;
        String time = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String threadName = Thread.currentThread().getName();
        System.out.printf("[%s] [%s] %s%n", time, threadName, message);
    }
}
