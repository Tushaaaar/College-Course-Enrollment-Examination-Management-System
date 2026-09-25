package com.college.management.ui;

import com.college.management.exception.InvalidMarksException;
import com.college.management.model.Enrollment;
import com.college.management.model.Exam;
import com.college.management.model.enums.ExamType;
import com.college.management.model.Mark;
import com.college.management.service.EnrollmentService;
import com.college.management.service.ExamService;
import com.college.management.service.MarkService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExamMenu {
    private ExamService examService;
    private MarkService markService;
    private EnrollmentService enrollmentService;
    private Scanner scanner;

    public ExamMenu(ExamService examService, MarkService markService, EnrollmentService enrollmentService, Scanner scanner) {
        this.examService = examService;
        this.markService = markService;
        this.enrollmentService = enrollmentService;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("===== EXAMINATION & MARKS =====");
            System.out.println("1. Create Exam");
            System.out.println("2. Enter Marks");
            System.out.println("3. Update Marks");
            System.out.println("4. View Marks by Student");
            System.out.println("5. View Marks by Exam");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    createExam();
                    break;
                case "2":
                    enterMarks();
                    break;
                case "3":
                    updateMarks();
                    break;
                case "4":
                    viewMarksByStudent();
                    break;
                case "5":
                    viewMarksByExam();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createExam() {
        try {
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            
            System.out.print("Exam Type (1=MIDTERM, 2=FINAL, 3=INTERNAL, 4=PRACTICAL): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());
            ExamType type = ExamType.MIDTERM;
            if (typeChoice == 2) type = ExamType.FINAL;
            else if (typeChoice == 3) type = ExamType.INTERNAL;
            else if (typeChoice == 4) type = ExamType.PRACTICAL;
            
            System.out.print("Exam Date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            
            System.out.print("Total Marks: ");
            int totalMarks = Integer.parseInt(scanner.nextLine());
            
            Exam exam = examService.createExam(courseCode, type, date, totalMarks);
            System.out.println("Exam created successfully with ID: " + exam.getExamId());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void enterMarks() {
        try {
            System.out.print("Exam ID: ");
            int examId = Integer.parseInt(scanner.nextLine());
            
            Exam exam = examService.getExam(examId);
            String courseCode = exam.getCourse().getCourseCode();
            
            List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseCode);
            if (enrollments.isEmpty()) {
                System.out.println("No students enrolled in this course.");
                return;
            }
            
            for (Enrollment e : enrollments) {
                System.out.print("Enter marks for " + e.getStudent().getName() + " (" + e.getStudent().getRollNumber() + "): ");
                String input = scanner.nextLine();
                if (input.trim().isEmpty()) continue;
                
                try {
                    double marks = Double.parseDouble(input);
                    markService.enterMark(examId, e.getStudent().getRollNumber(), marks);
                }
        catch (InvalidMarksException ex) {
                    System.out.println("Invalid marks: " + ex.getMessage());
                }
            }
            System.out.println("Marks entered successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateMarks() {
        try {
            System.out.print("Exam ID: ");
            int examId = Integer.parseInt(scanner.nextLine());
            System.out.print("Student Roll Number: ");
            String rollNumber = scanner.nextLine();
            System.out.print("New Marks: ");
            double newMarks = Double.parseDouble(scanner.nextLine());
            
            markService.updateMark(examId, rollNumber, newMarks);
            System.out.println("Marks updated successfully.");
        }
        catch (InvalidMarksException e) {
            System.out.println("Invalid marks: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewMarksByStudent() {
        try {
            System.out.print("Student Roll Number: ");
            String rollNumber = scanner.nextLine();
            List<Mark> marks = markService.getMarksByStudent(rollNumber);
            
            if (marks.isEmpty()) {
                System.out.println("No marks found.");
                return;
            }
            
            for (Mark m : marks) {
                System.out.println("Exam ID: " + m.getExam().getExamId() + " | Marks: " + m.getMarksObtained() + "/" + m.getExam().getTotalMarks());
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewMarksByExam() {
        try {
            System.out.print("Exam ID: ");
            int examId = Integer.parseInt(scanner.nextLine());
            List<Mark> marks = markService.getMarksByExam(examId);
            
            if (marks.isEmpty()) {
                System.out.println("No marks found for this exam.");
                return;
            }
            
            for (Mark m : marks) {
                System.out.println("Student: " + m.getStudent().getRollNumber() + " - " + m.getStudent().getName() + " | Marks: " + m.getMarksObtained());
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
