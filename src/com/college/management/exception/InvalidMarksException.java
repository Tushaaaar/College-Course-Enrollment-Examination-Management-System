package com.college.management.exception;

public class InvalidMarksException extends Exception {
    public InvalidMarksException(double marks, int totalMarks) {
        super("Invalid marks: " + marks + ". Total marks: " + totalMarks);
    }
}
