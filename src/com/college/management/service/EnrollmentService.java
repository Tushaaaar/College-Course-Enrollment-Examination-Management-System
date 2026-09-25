package com.college.management.service;

import com.college.management.exception.CourseFullException;
import com.college.management.exception.DuplicateEnrollmentException;
import com.college.management.exception.StudentNotFoundException;
import com.college.management.model.Course;
import com.college.management.model.Enrollment;
import com.college.management.model.enums.EnrollmentStatus;
import com.college.management.model.Student;
import com.college.management.repository.CourseRepository;
import com.college.management.repository.EnrollmentRepository;
import com.college.management.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class EnrollmentService {
    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private static int nextId = 1;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public void enrollStudent(String rollNumber, String courseCode) throws CourseFullException, DuplicateEnrollmentException {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        // check if course is full
        if (course.isFull()) {
            throw new CourseFullException(courseCode);
        }
        
        Optional<Enrollment> existing = enrollmentRepository.findByStudentAndCourse(student.getId(), course.getId());
        if (existing.isPresent() && existing.get().getStatus() == EnrollmentStatus.ACTIVE) {
            throw new DuplicateEnrollmentException(rollNumber, courseCode);
        }
        
        Enrollment enrollment = new Enrollment(nextId++, student, course);
        enrollmentRepository.add(enrollment);
        course.incrementEnrollment();
    }

    public void dropStudent(String rollNumber, String courseCode) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student.getId(), course.getId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        
        enrollment.drop();
        course.decrementEnrollment();
    }

    public List<Enrollment> getStudentEnrollments(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
        return enrollmentRepository.findByStudent(student.getId());
    }

    public List<Enrollment> getCourseEnrollments(String courseCode) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        return enrollmentRepository.findByCourse(course.getId());
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
