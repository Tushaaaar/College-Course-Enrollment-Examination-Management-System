package com.college.management.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String rollNumber) {
        super("Student not found with roll number: " + rollNumber);
    }
}
