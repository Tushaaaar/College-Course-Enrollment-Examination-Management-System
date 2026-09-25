package com.college.management.model;

import com.college.management.interfaces.Displayable;
import com.college.management.interfaces.Searchable;
import com.college.management.model.enums.CourseType;
import java.util.Objects;
public class Course implements Comparable<Course>, Searchable<Course>, Displayable {
    private int id;
    private String courseCode;
    private String courseName;
    private int credits;
    private int maxCapacity;
    private int currentEnrollment = 0;
    private CourseType type;
    private String department;

    public Course(int id, String courseCode, String courseName, int credits, int maxCapacity, CourseType type, String department) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.maxCapacity = maxCapacity;
        this.type = type;
        this.department = department;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public int getCurrentEnrollment() { return currentEnrollment; }
    public void setCurrentEnrollment(int currentEnrollment) { this.currentEnrollment = currentEnrollment; }

    public CourseType getType() { return type; }
    public void setType(CourseType type) { this.type = type; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public void incrementEnrollment() {
        currentEnrollment++;
    }

    public void decrementEnrollment() {
        if (currentEnrollment > 0) {
            currentEnrollment--;
        }
    }
    public boolean isFull() {
        return currentEnrollment >= maxCapacity;
    }

    @Override
    public int compareTo(Course other) {
        return this.courseCode.compareTo(other.courseCode);
    }

    @Override
    public boolean matches(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return courseCode.toLowerCase().contains(lowerKeyword) ||
               courseName.toLowerCase().contains(lowerKeyword);
    }

    @Override
    public String getSearchKey() {
        return courseCode;
    }

    @Override
    public String toDisplayString() {
        return "Course Code: " + courseCode + "\n" +
               "Name: " + courseName + "\n" +
               "Credits: " + credits + "\n" +
               "Department: " + department + "\n" +
               "Type: " + type + "\n" +
               "Enrollment: " + currentEnrollment + "/" + maxCapacity;
    }

    @Override
    public String toTableRow() {
        return String.format("| %-10s | %-25s | %-7d | %-12s | %-10s |", 
            courseCode, courseName, credits, currentEnrollment + "/" + maxCapacity, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }
}
