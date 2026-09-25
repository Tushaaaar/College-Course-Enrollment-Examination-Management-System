package com.college.management.exception;

import com.college.management.model.enums.ExamType;

public class ExamNotFoundException extends RuntimeException {
    public ExamNotFoundException(String courseCode, ExamType type) {
        super("Exam of type " + type + " not found for course: " + courseCode);
    }
}
