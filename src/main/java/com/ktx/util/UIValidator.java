package com.ktx.util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for UI validation
 */
public class UIValidator {
    
    /**
     * Validate a form and show error messages
     * 
     * @param parent parent component for dialogs
     * @param components list of form components to validate
     * @return true if all validations pass, false otherwise
     */
    public static boolean validateForm(Component parent, FormComponent... components) {
        List<String> errors = new ArrayList<>();
        
        for (FormComponent component : components) {
            String error = component.getValidationError();
            if (error != null) {
                errors.add(error);
            }
        }
        
        if (!errors.isEmpty()) {
            showValidationErrors(parent, errors);
            return false;
        }
        
        return true;
    }
    
    /**
     * Show validation errors in a dialog
     */
    private static void showValidationErrors(Component parent, List<String> errors) {
        StringBuilder message = new StringBuilder("Vui lòng sửa các lỗi sau:\\n\\n");
        for (int i = 0; i < errors.size(); i++) {
            message.append((i + 1)).append(". ").append(errors.get(i));
            if (i < errors.size() - 1) {
                message.append("\\n");
            }
        }
        
        JOptionPane.showMessageDialog(parent, 
            message.toString(), 
            "Lỗi xác thực", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Interface for form components that can be validated
     */
    public interface FormComponent {
        String getValidationError();
        void highlightError();
        void clearHighlight();
    }
    
    /**
     * Validatable text field
     */
    public static class ValidatableTextField extends JTextField implements FormComponent {
        private String fieldName;
        private boolean required;
        private java.util.function.Predicate<String> customValidator;
        
        public ValidatableTextField(String fieldName, boolean required) {
            this.fieldName = fieldName;
            this.required = required;
        }
        
        public void setCustomValidator(java.util.function.Predicate<String> validator) {
            this.customValidator = validator;
        }
        
        @Override
        public String getValidationError() {
            String value = getText().trim();
            
            if (required && value.isEmpty()) {
                return fieldName + " không được rỗng";
            }
            
            if (!value.isEmpty() && customValidator != null && !customValidator.test(value)) {
                return fieldName + " không hợp lệ";
            }
            
            return null;
        }
        
        @Override
        public void highlightError() {
            setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            setBackground(new Color(255, 240, 240));
        }
        
        @Override
        public void clearHighlight() {
            setBorder(UIManager.getBorder("TextField.border"));
            setBackground(Color.WHITE);
        }
    }
    
    /**
     * Validatable combo box
     */
    public static class ValidatableComboBox<T> extends JComboBox<T> implements FormComponent {
        private String fieldName;
        private boolean required;
        
        public ValidatableComboBox(String fieldName, boolean required) {
            this.fieldName = fieldName;
            this.required = required;
        }
        
        @Override
        public String getValidationError() {
            if (required && getSelectedItem() == null) {
                return fieldName + " không được rỗng";
            }
            return null;
        }
        
        @Override
        public void highlightError() {
            setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            setBackground(new Color(255, 240, 240));
        }
        
        @Override
        public void clearHighlight() {
            setBorder(UIManager.getBorder("ComboBox.border"));
            setBackground(Color.WHITE);
        }
    }
    
    /**
     * Clear all highlights
     */
    public static void clearHighlights(FormComponent... components) {
        for (FormComponent component : components) {
            component.clearHighlight();
        }
    }
}
