package com.messaging.exception;

import com.messaging.util.Logger;

/**
 * Centralized exception handling logic and can be expanded to
 * catch application specific custom errors.
 * 
 * @author Rahul Shukla
 */
public class ExceptionHandler extends Throwable {
    public static void handle(String context, Exception exception) {
        Logger.error(context, "Exception :: " + exception.getClass().getSimpleName() + " - " + exception.getMessage());
    }
}
