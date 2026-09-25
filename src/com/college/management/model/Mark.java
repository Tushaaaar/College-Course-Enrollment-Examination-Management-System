package com.college.management.model;
public class Mark {
    private int id;
    private Exam exam;
    private Student student;
    private double marksObtained;

    public Mark(int id, Exam exam, Student student, double marksObtained) {
        this.id = id;
        this.exam = exam;
        this.student = student;
        this.marksObtained = marksObtained;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(double marksObtained) { this.marksObtained = marksObtained; }

    @Override
    public String toString() {
        return "Mark [Student=" + student.getRollNumber() + 
               ", Exam=" + exam.getExamId() + 
               ", Marks=" + marksObtained + "]";
    }
}
