package com.college.management.util;
public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public static boolean isNonEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValidMarks(double marks, int totalMarks) {
        return marks >= 0 && marks <= totalMarks;
    }
}
