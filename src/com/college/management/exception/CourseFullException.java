package com.college.management.exception;

public class CourseFullException extends Exception {
    public CourseFullException(String courseCode) {
        super("Course is full: " + courseCode);
    }
}
