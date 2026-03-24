package com.ktx.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for validation operations
 */
public class ValidationUtil {
    
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();
    
    /**
     * Validate an object and return all validation errors
     * 
     * @param object the object to validate
     * @return string containing all validation error messages
     */
    public static String validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        
        if (violations.isEmpty()) {
            return null;
        }
        
        return violations.stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("\\n"));
    }
    
    /**
     * Check if an object is valid
     * 
     * @param object the object to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValid(Object object) {
        return validator.validate(object).isEmpty();
    }
    
    /**
     * Validate password against security policy
     * 
     * @param password the password to validate
     * @return validation error message or null if valid
     */
    public static String validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự";
        }
        
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{}|;:<>?,./".indexOf(c) >= 0);
        
        if (!hasUpper) {
            return "Mật khẩu phải có ít nhất một chữ hoa";
        }
        
        if (!hasLower) {
            return "Mật khẩu phải có ít nhất một chữ thường";
        }
        
        if (!hasDigit) {
            return "Mật khẩu phải có ít nhất một chữ số";
        }
        
        if (!hasSpecial) {
            return "Mật khẩu phải có ít nhất một ký tự đặc biệt";
        }
        
        return null;
    }
}
