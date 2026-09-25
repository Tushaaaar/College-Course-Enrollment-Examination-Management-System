package com.college.management.exception;

public class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String rollNumber, String courseCode) {
        super("Student " + rollNumber + " is already enrolled in course " + courseCode);
    }
}
