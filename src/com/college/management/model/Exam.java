package com.college.management.model;

import com.college.management.interfaces.Displayable;
import com.college.management.model.enums.ExamType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
public class Exam implements Displayable {
    private int examId;
    private Course course;
    private ExamType type;
    private LocalDate examDate;
    private int totalMarks;
    private double[] marksheet;

    public Exam(int examId, Course course, ExamType type, LocalDate examDate, int totalMarks) {
        this.examId = examId;
        this.course = course;
        this.type = type;
        this.examDate = examDate;
        this.totalMarks = totalMarks;
        this.marksheet = new double[course.getMaxCapacity()];
        Arrays.fill(this.marksheet, -1.0);
    }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public ExamType getType() { return type; }
    public void setType(ExamType type) { this.type = type; }

    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public double[] getMarksheet() { return marksheet; }
    public void setMarksheet(double[] marksheet) { this.marksheet = marksheet; }

    public void setMark(int index, double mark) {
        if (index >= 0 && index < marksheet.length) {
            marksheet[index] = mark;
        }
    }

    public double getMark(int index) {
        if (index >= 0 && index < marksheet.length) {
            return marksheet[index];
        }
        return -1.0;
    }

    @Override
    public String toDisplayString() {
        return "Exam ID: " + examId + "\n" +
               "Course Code: " + course.getCourseCode() + "\n" +
               "Type: " + type + "\n" +
               "Date: " + examDate + "\n" +
               "Total Marks: " + totalMarks;
    }

    @Override
    public String toTableRow() {
        return String.format("| %-8d | %-12s | %-10s | %-12s | %-10d |", 
            examId, course.getCourseCode(), type, examDate, totalMarks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return examId == exam.examId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId);
    }
}
