package com.college.management.service;

import com.college.management.model.Course;
import com.college.management.model.CourseAssignment;
import com.college.management.model.Faculty;
import com.college.management.repository.CourseAssignmentRepository;
import com.college.management.repository.CourseRepository;
import com.college.management.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.List;

// faculty management
public class FacultyService {
    private FacultyRepository facultyRepository;
    private CourseAssignmentRepository assignmentRepository;
    private CourseRepository courseRepository;
    private static int nextId = 1;

    public FacultyService(FacultyRepository facultyRepository, CourseAssignmentRepository assignmentRepository, CourseRepository courseRepository) {
        this.facultyRepository = facultyRepository;
        this.assignmentRepository = assignmentRepository;
        this.courseRepository = courseRepository;
    }

    public Faculty addFaculty(String name, String email, String phone, String employeeId, String designation, String specialization) {
        Faculty faculty = new Faculty(nextId++, name, email, phone, employeeId, designation, specialization);
        facultyRepository.add(faculty);
        return faculty;
    }

    public void updateFaculty(String employeeId, String name, String email, String phone, String designation, String specialization) {
        Faculty faculty = getFaculty(employeeId);
        faculty.setName(name);
        faculty.setEmail(email);
        faculty.setPhone(phone);
        faculty.setDesignation(designation);
        faculty.setSpecialization(specialization);
        facultyRepository.update(faculty);
    }

    public void deleteFaculty(String employeeId) {
        Faculty faculty = getFaculty(employeeId);
        faculty.setActive(false);
        facultyRepository.update(faculty);
    }

    public Faculty getFaculty(String employeeId) {
        return facultyRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Faculty not found: " + employeeId));
    }

    public List<Faculty> getAllFaculty() {
        List<Faculty> active = new ArrayList<>();
        for (Faculty f : facultyRepository.findAll()) {
            if (f.isActive()) active.add(f);
        }
        return active;
    }

    public void assignToCourse(String employeeId, String courseCode, String academicYear) {
        Faculty faculty = getFaculty(employeeId);
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        if (assignmentRepository.findByFacultyAndCourse(faculty.getId(), course.getId()).isPresent()) {
            throw new RuntimeException("Faculty already assigned to course");
        }
        
        CourseAssignment assignment = new CourseAssignment(nextId++, faculty, course, academicYear);
        assignmentRepository.add(assignment);
    }

    public void removeFromCourse(String employeeId, String courseCode) {
        Faculty faculty = getFaculty(employeeId);
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        CourseAssignment assignment = assignmentRepository.findByFacultyAndCourse(faculty.getId(), course.getId())
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignmentRepository.delete(assignment);
    }

    public List<CourseAssignment> getFacultyAssignments(String employeeId) {
        Faculty faculty = getFaculty(employeeId);
        return assignmentRepository.findByFaculty(faculty.getId());
    }

    public List<Faculty> searchFaculty(String keyword) {
        List<Faculty> found = new ArrayList<>();
        for (Faculty f : facultyRepository.findAll()) {
            if (f.matches(keyword)) found.add(f);
        }
        return found;
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
