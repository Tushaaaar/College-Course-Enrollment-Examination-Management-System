package com.college.management.service;

import com.college.management.model.Course;
import com.college.management.model.enums.CourseType;
import com.college.management.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private CourseRepository courseRepository;
    private static int nextId = 1;

    public CourseService(CourseRepository repo) {
        this.courseRepository = repo;
    }

    public Course addCourse(String code, String name, int credits, int capacity, CourseType type, String department) {
        Course course = new Course(nextId++, code, name, credits, capacity, type, department);
        courseRepository.add(course);
        return course;
    }

    public void updateCourse(String courseCode, String name, int credits, int capacity, CourseType type, String department) {
        Course course = getCourse(courseCode);
        course.setCourseName(name);
        course.setCredits(credits);
        course.setMaxCapacity(capacity);
        course.setType(type);
        course.setDepartment(department);
    }

    public void deleteCourse(String courseCode) {
        Course course = getCourse(courseCode);
        courseRepository.findAll().remove(course); // Given no BaseRepository and delete method, clear manually if possible, or remove from list. Assuming CourseRepository has delete
    }

    public Course getCourse(String courseCode) {
        return courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> searchCourses(String keyword) {
        List<Course> found = new ArrayList<>();
        for (Course c : courseRepository.findAll()) {
            if (c.matches(keyword)) found.add(c);
        }
        return found;
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
