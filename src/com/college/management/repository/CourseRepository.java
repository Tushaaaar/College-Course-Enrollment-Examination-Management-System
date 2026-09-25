package com.college.management.repository;

import com.college.management.model.Course;
import java.util.*;
import java.util.stream.Collectors;

// using hashmap here
public class CourseRepository {
    private Map<String, Course> courseMap = new HashMap<>();

    public void add(Course course) { courseMap.put(course.getCourseCode(), course); }
    public void update(Course course) { courseMap.put(course.getCourseCode(), course); }
    public void delete(String courseCode) { courseMap.remove(courseCode); }
    
    public Optional<Course> findByCode(String courseCode) { 
        return Optional.ofNullable(courseMap.get(courseCode)); 
    }
    
    public Optional<Course> findById(int id) {
        for (Course c : courseMap.values()) {
            if (c.getId() == id) return Optional.of(c);
        }
        return Optional.empty();
    }
    public List<Course> findAll() { return new ArrayList<>(courseMap.values()); }
    public List<Course> findByDepartment(String dept) {
        return courseMap.values().stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(dept))
                .collect(Collectors.toList());
    }
    
    public int count() { return courseMap.size(); }
    public void clear() { courseMap.clear(); }
}
