package com.messaging.enums;

/**
 * Enum to represent different levels of logging and to be used with
 * Logger to print messages with specific severity.
 *
 * Levels:
 * - INFO: General information about application flow.
 * - DEBUG: Detailed information for debugging.
 * - ERROR: Error conditions or exceptions.
 * - WARN: Potential issues that aren’t necessarily errors.
 * - TRACE: Very fine-grained info (e.g., entering/exiting methods).
 *
 * @author Rahul
 */
public enum LogLevel {
    INFO,
    DEBUG,
    ERROR,
    WARN,
    TRACE
}