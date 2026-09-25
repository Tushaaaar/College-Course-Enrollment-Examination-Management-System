package com.college.management.model;
public class CourseAssignment {
    private int id;
    private Faculty faculty;
    private Course course;
    private String academicYear;

    public CourseAssignment(int id, Faculty faculty, Course course, String academicYear) {
        this.id = id;
        this.faculty = faculty;
        this.course = course;
        this.academicYear = academicYear;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Faculty getFaculty() { return faculty; }
    public void setFaculty(Faculty faculty) { this.faculty = faculty; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    @Override
    public String toString() {
        return "CourseAssignment [Faculty=" + faculty.getEmployeeId() + 
               ", Course=" + course.getCourseCode() + 
               ", Year=" + academicYear + "]";
    }
}
