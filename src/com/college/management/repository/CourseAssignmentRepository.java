package com.college.management.repository;

import com.college.management.model.CourseAssignment;
import java.util.*;
import java.util.stream.Collectors;
public class CourseAssignmentRepository {
    private List<CourseAssignment> assignments = new ArrayList<>();

    public void add(CourseAssignment a) { assignments.add(a); }
    public List<CourseAssignment> findByFaculty(int facultyId) {
        return assignments.stream().filter(a -> a.getFaculty().getId() == facultyId).collect(Collectors.toList());
    }
    public List<CourseAssignment> findByCourse(int courseId) {
        return assignments.stream().filter(a -> a.getCourse().getId() == courseId).collect(Collectors.toList());
    }
    
    public Optional<CourseAssignment> findByFacultyAndCourse(int facultyId, int courseId) {
        return assignments.stream()
                .filter(a -> a.getFaculty().getId() == facultyId && a.getCourse().getId() == courseId)
                .findFirst();
    }
    
    public void delete(CourseAssignment a) { assignments.remove(a); }
    public List<CourseAssignment> findAll() { return new ArrayList<>(assignments); }
    
    public int count() { return assignments.size(); }
    
    public void clear() { assignments.clear(); }
}
