package com.college.management.service;

import com.college.management.exception.ExamNotFoundException;
import com.college.management.model.Course;
import com.college.management.model.Exam;
import com.college.management.model.enums.ExamType;
import com.college.management.repository.CourseRepository;
import com.college.management.repository.ExamRepository;

import java.time.LocalDate;
import java.util.List;

public class ExamService {
    private ExamRepository examRepository;
    private CourseRepository courseRepository;
    private static int nextId = 1;

    public ExamService(ExamRepository examRepository, CourseRepository courseRepository) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
    }

    public Exam createExam(String courseCode, ExamType type, LocalDate date, int totalMarks) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        if (examRepository.findByCourseAndType(course.getId(), type).isPresent()) {
            throw new RuntimeException("Exam already exists for course " + courseCode + " and type " + type);
        }
        
        Exam exam = new Exam(nextId++, course, type, date, totalMarks);
        examRepository.add(exam);
        return exam;
    }

    public Exam getExam(int examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("Unknown", null)); // Can't easily recover course code here unless extending exception
    }

    public List<Exam> getExamsByCourse(String courseCode) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        return examRepository.findByCourse(course.getId());
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
