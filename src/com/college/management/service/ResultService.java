package com.college.management.service;

import com.college.management.exception.StudentNotFoundException;
import com.college.management.model.Course;
import com.college.management.model.Enrollment;
import com.college.management.model.Exam;
import com.college.management.model.Mark;
import com.college.management.model.Result;
import com.college.management.model.Student;
import com.college.management.repository.CourseRepository;
import com.college.management.repository.EnrollmentRepository;
import com.college.management.repository.ExamRepository;
import com.college.management.repository.MarkRepository;
import com.college.management.repository.ResultRepository;
import com.college.management.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResultService {
    private ResultRepository resultRepository;
    private MarkRepository markRepository;
    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private ExamRepository examRepository;
    private static int nextId = 1;

    public ResultService(ResultRepository resultRepository, MarkRepository markRepository, EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository, ExamRepository examRepository) {
        this.resultRepository = resultRepository;
        this.markRepository = markRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.examRepository = examRepository;
    }

    public Result generateResult(String rollNumber, String courseCode) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        List<Exam> exams = examRepository.findByCourse(course.getId());
        Result result = new Result(nextId++, student, course);
        
        int totalPossible = 0;
        
        for (Exam exam : exams) {
            Optional<Mark> mark = markRepository.findByExamAndStudent(exam.getExamId(), student.getId());
            if (mark.isPresent()) {
                result.addExamMark(exam.getType(), mark.get().getMarksObtained());
            }
            totalPossible += exam.getTotalMarks();
        }
        
        if (totalPossible > 0) {
            result.calculatePercentage(totalPossible);
            result.calculateGrade();
        }
        
        resultRepository.add(result);
        return result;
    }

    public List<Result> generateCourseResults(String courseCode) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course.getId());
        List<Result> results = new ArrayList<>();
        
        for (Enrollment enrollment : enrollments) {
            results.add(generateResult(enrollment.getStudent().getRollNumber(), courseCode));
        }
        
        return results;
    }

    public List<Result> getToppers(int n) {
        return resultRepository.getTopN(n);
    }

    public List<Result> getStudentResults(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
        return resultRepository.findByStudent(student.getId());
    }

    public List<Result> getCourseResults(String courseCode) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        return resultRepository.findByCourse(course.getId());
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
