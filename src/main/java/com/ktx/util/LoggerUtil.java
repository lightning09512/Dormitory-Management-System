package com.ktx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for logging operations
 */
public class LoggerUtil {
    
    /**
     * Get logger for a class
     * 
     * @param clazz the class to get logger for
     * @return logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * Log method entry with parameters
     * 
     * @param logger the logger
     * @param methodName method name
     * @param params method parameters
     */
    public static void logEntry(Logger logger, String methodName, Object... params) {
        if (logger.isDebugEnabled()) {
            String paramsStr = params.length > 0 ? 
                " with params: " + java.util.Arrays.toString(params) : "";
            logger.debug("Entering {}{}", methodName + paramsStr);
        }
    }
    
    /**
     * Log method exit with result
     * 
     * @param logger the logger
     * @param methodName method name
     * @param result method result
     */
    public static void logExit(Logger logger, String methodName, Object result) {
        if (logger.isDebugEnabled()) {
            logger.debug("Exiting {} with result: {}", methodName, result);
        }
    }
    
    /**
     * Log method exit without result (void method)
     * 
     * @param logger the logger
     * @param methodName method name
     */
    public static void logExit(Logger logger, String methodName) {
        if (logger.isDebugEnabled()) {
            logger.debug("Exiting {}", methodName);
        }
    }
    
    /**
     * Log exception with stack trace
     * 
     * @param logger the logger
     * @param methodName method name
     * @param exception the exception
     */
    public static void logError(Logger logger, String methodName, Exception exception) {
        logger.error("Error in {}: {}", methodName, exception.getMessage(), exception);
    }
    
    /**
     * Log validation error
     * 
     * @param logger the logger
     * @param methodName method name
     * @param validationErrors validation errors
     */
    public static void logValidationError(Logger logger, String methodName, String validationErrors) {
        logger.warn("Validation error in {}: {}", methodName, validationErrors);
    }
}
