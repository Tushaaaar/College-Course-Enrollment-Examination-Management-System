package com.college.management.repository;

import com.college.management.model.Enrollment;
import java.util.*;
import java.util.stream.Collectors;

// using linked list here
public class EnrollmentRepository {
    private LinkedList<Enrollment> enrollments = new LinkedList<>();

    public void add(Enrollment enrollment) { enrollments.add(enrollment); }
    public List<Enrollment> findByStudent(int studentId) {
        return enrollments.stream().filter(e -> e.getStudent().getId() == studentId).collect(Collectors.toList());
    }
    public List<Enrollment> findByCourse(int courseId) {
        return enrollments.stream().filter(e -> e.getCourse().getId() == courseId).collect(Collectors.toList());
    }
    
    public Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId) {
        return enrollments.stream()
                .filter(e -> e.getStudent().getId() == studentId && e.getCourse().getId() == courseId)
                .findFirst();
    }
    
    public void delete(Enrollment enrollment) { enrollments.remove(enrollment); }
    public List<Enrollment> findAll() { return new LinkedList<>(enrollments); }
    
    public int count() { return enrollments.size(); }
    
    public void clear() { enrollments.clear(); }
}
