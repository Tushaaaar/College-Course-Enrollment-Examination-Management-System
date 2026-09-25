package com.college.management.ui;

import com.college.management.exception.CourseFullException;
import com.college.management.exception.DuplicateEnrollmentException;
import com.college.management.model.Enrollment;
import com.college.management.service.EnrollmentService;

import java.util.List;
import java.util.Scanner;

public class EnrollmentMenu {
    private EnrollmentService enrollmentService;
    private Scanner scanner;

    public EnrollmentMenu(EnrollmentService enrollmentService, Scanner scanner) {
        this.enrollmentService = enrollmentService;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("===== ENROLLMENT =====");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Drop Student from Course");
            System.out.println("3. View Student's Enrollments");
            System.out.println("4. View Course's Enrolled Students");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    enrollStudent();
                    break;
                case "2":
                    dropStudent();
                    break;
                case "3":
                    viewStudentEnrollments();
                    break;
                case "4":
                    viewCourseEnrollments();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void enrollStudent() {
        try {
            System.out.print("Student Roll Number: ");
            String rollNumber = scanner.nextLine();
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            
            enrollmentService.enrollStudent(rollNumber, courseCode);
            System.out.println("Student enrolled successfully.");
        }
        catch (CourseFullException | DuplicateEnrollmentException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void dropStudent() {
        try {
            System.out.print("Student Roll Number: ");
            String rollNumber = scanner.nextLine();
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            
            enrollmentService.dropStudent(rollNumber, courseCode);
            System.out.println("Student dropped from course successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewStudentEnrollments() {
        try {
            System.out.print("Student Roll Number: ");
            String rollNumber = scanner.nextLine();
            List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(rollNumber);
            
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this student.");
                return;
            }
            
            for (Enrollment e : enrollments) {
                System.out.println("Course: " + e.getCourse().getCourseCode() + " - " + e.getCourse().getCourseName() + " | Status: " + e.getStatus());
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewCourseEnrollments() {
        try {
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseCode);
            
            if (enrollments.isEmpty()) {
                System.out.println("No students enrolled in this course.");
                return;
            }
            
            for (Enrollment e : enrollments) {
                System.out.println("Student: " + e.getStudent().getRollNumber() + " - " + e.getStudent().getName() + " | Status: " + e.getStatus());
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
