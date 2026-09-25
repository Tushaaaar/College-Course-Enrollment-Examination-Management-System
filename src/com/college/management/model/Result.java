package com.college.management.model;

import com.college.management.interfaces.Displayable;
import com.college.management.interfaces.Exportable;
import com.college.management.model.enums.ExamType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class Result implements Comparable<Result>, Displayable, Exportable {
    private int id;
    private Student student;
    private Course course;
    private Map<ExamType, Double> examMarks;
    private double totalPercentage;
    private char grade;
    private boolean passed;

    public Result(int id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.examMarks = new HashMap<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Map<ExamType, Double> getExamMarks() { return examMarks; }
    public void setExamMarks(Map<ExamType, Double> examMarks) { this.examMarks = examMarks; }

    public double getTotalPercentage() { return totalPercentage; }
    public void setTotalPercentage(double totalPercentage) { this.totalPercentage = totalPercentage; }

    public char getGrade() { return grade; }
    public void setGrade(char grade) { this.grade = grade; }

    public boolean isPassed() { return passed; }
    public void setPassed(boolean passed) { this.passed = passed; }

    public void addExamMark(ExamType type, double marks) {
        examMarks.put(type, marks);
    }
    public void calculatePercentage(int totalPossiblePerExam) {
        double sum = 0;
        for (double value : examMarks.values()) {
            sum += value;
        }
        int count = examMarks.size();
        if (count == 0) {
            totalPercentage = 0;
        } else {
            totalPercentage = (sum / (count * totalPossiblePerExam)) * 100;
        }
        calculateGrade();
    }

    // grading
    public void calculateGrade() {
        if (totalPercentage >= 90) {
            grade = 'A';
        } else if (totalPercentage >= 80) {
            grade = 'B';
        } else if (totalPercentage >= 70) {
            grade = 'C';
        } else if (totalPercentage >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }
        passed = (grade != 'F');
    }

    @Override
    public int compareTo(Result other) {
        return Double.compare(other.totalPercentage, this.totalPercentage);
    }

    @Override
    public String toDisplayString() {
        return "Student: " + student.getName() + " (" + student.getRollNumber() + ")\n" +
               "Course: " + course.getCourseCode() + "\n" +
               "Percentage: " + String.format("%.2f", totalPercentage) + "%\n" +
               "Grade: " + grade + "\n" +
               "Status: " + (passed ? "Passed" : "Failed");
    }

    @Override
    public String toTableRow() {
        return String.format("| %-12s | %-20s | %-10s | %-10.2f | %-5c | %-10s |", 
            student.getRollNumber(), student.getName(), course.getCourseCode(), 
            totalPercentage, grade, (passed ? "Pass" : "Fail"));
    }

    @Override
    public String toCsvRow() {
        return student.getRollNumber() + "," + student.getName() + "," + 
               course.getCourseCode() + "," + totalPercentage + "," + grade + "," + (passed ? "Pass" : "Fail");
    }

    @Override
    public String[] getCsvHeaders() {
        return new String[]{"Roll Number", "Student Name", "Course Code", "Percentage", "Grade", "Status"};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(student.getRollNumber(), result.student.getRollNumber()) &&
               Objects.equals(course.getCourseCode(), result.course.getCourseCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(student.getRollNumber(), course.getCourseCode());
    }
}
