package com.college.management.model;

import com.college.management.model.enums.EnrollmentStatus;
import java.time.LocalDate;
public class Enrollment {
    private int id;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;

    public Enrollment(int id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.status = EnrollmentStatus.ACTIVE;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public EnrollmentStatus getStatus() { return status; }
    public void setStatus(EnrollmentStatus status) { this.status = status; }

    public void drop() {
        this.status = EnrollmentStatus.DROPPED;
    }

    public void complete() {
        this.status = EnrollmentStatus.COMPLETED;
    }

    @Override
    public String toString() {
        return "Enrollment [Student=" + student.getRollNumber() + 
               ", Course=" + course.getCourseCode() + 
               ", Status=" + status + 
               ", Date=" + enrollmentDate + "]";
    }
}
