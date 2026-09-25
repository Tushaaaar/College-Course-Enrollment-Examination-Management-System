package com.college.management.ui;

import java.util.Scanner;

public class MainMenu {
    private StudentMenu studentMenu;
    private CourseMenu courseMenu;
    private FacultyMenu facultyMenu;
    private EnrollmentMenu enrollmentMenu;
    private ExamMenu examMenu;
    private ResultMenu resultMenu;
    private SearchMenu searchMenu;
    private Scanner scanner;

    public MainMenu(StudentMenu studentMenu, CourseMenu courseMenu, FacultyMenu facultyMenu, EnrollmentMenu enrollmentMenu, ExamMenu examMenu, ResultMenu resultMenu, SearchMenu searchMenu, Scanner scanner) {
        this.studentMenu = studentMenu;
        this.courseMenu = courseMenu;
        this.facultyMenu = facultyMenu;
        this.enrollmentMenu = enrollmentMenu;
        this.examMenu = examMenu;
        this.resultMenu = resultMenu;
        this.searchMenu = searchMenu;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("========================================");
            System.out.println("  COLLEGE COURSE & EXAMINATION");
            System.out.println("  MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Faculty Management");
            System.out.println("4. Enrollment");
            System.out.println("5. Examination & Marks");
            System.out.println("6. Results & Reports");
            System.out.println("7. Search");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    studentMenu.show();
                    break;
                case "2":
                    courseMenu.show();
                    break;
                case "3":
                    facultyMenu.show();
                    break;
                case "4":
                    enrollmentMenu.show();
                    break;
                case "5":
                    examMenu.show();
                    break;
                case "6":
                    resultMenu.show();
                    break;
                case "7":
                    searchMenu.show();
                    break;
                case "0":
                    System.out.println("Goodbye! Data has been saved.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
