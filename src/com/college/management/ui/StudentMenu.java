package com.college.management.ui;

import com.college.management.exception.StudentNotFoundException;
import com.college.management.model.Student;
import com.college.management.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private StudentService studentService;
    private Scanner scanner;

    public StudentMenu(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("===== STUDENT MANAGEMENT =====");
            System.out.println("1. Register Student");
            System.out.println("2. Update Student");
            System.out.println("3. Delete Student");
            System.out.println("4. View Student");
            System.out.println("5. List All Students");
            System.out.println("6. Search Students");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    registerStudent();
                    break;
                case "2":
                    updateStudent();
                    break;
                case "3":
                    deleteStudent();
                    break;
                case "4":
                    viewStudent();
                    break;
                case "5":
                    listAllStudents();
                    break;
                case "6":
                    searchStudents();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void registerStudent() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Roll Number: ");
            String rollNumber = scanner.nextLine();
            System.out.print("Department: ");
            String department = scanner.nextLine();
            System.out.print("Semester: ");
            int semester = Integer.parseInt(scanner.nextLine());
            
            studentService.registerStudent(name, email, phone, rollNumber, department, semester);
            System.out.println("Student registered successfully.");
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input for semester. Please enter a valid number.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateStudent() {
        try {
            System.out.print("Enter Roll Number of student to update: ");
            String rollNumber = scanner.nextLine();
            System.out.print("New Name: ");
            String name = scanner.nextLine();
            System.out.print("New Email: ");
            String email = scanner.nextLine();
            System.out.print("New Phone: ");
            String phone = scanner.nextLine();
            System.out.print("New Department: ");
            String department = scanner.nextLine();
            System.out.print("New Semester: ");
            int semester = Integer.parseInt(scanner.nextLine());
            
            studentService.updateStudent(rollNumber, name, email, phone, department, semester);
            System.out.println("Student updated successfully.");
        }
        catch (StudentNotFoundException e) {
            System.out.println("Student not found.");
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input. Semester must be a number.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            System.out.print("Enter Roll Number of student to delete: ");
            String rollNumber = scanner.nextLine();
            studentService.deleteStudent(rollNumber);
            System.out.println("Student deleted successfully.");
        }
        catch (StudentNotFoundException e) {
            System.out.println("Student not found.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewStudent() {
        try {
            System.out.print("Enter Roll Number: ");
            String rollNumber = scanner.nextLine();
            Student student = studentService.getStudent(rollNumber);
            System.out.println(student.toDisplayString());
        }
        catch (StudentNotFoundException e) {
            System.out.println("Student not found.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.println("ID | Name | Roll Number | Department | Semester");
        for (Student s : students) {
            System.out.println(s.toTableRow());
        }
    }

    private void searchStudents() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        List<Student> students = studentService.searchStudents(keyword);
        if (students.isEmpty()) {
            System.out.println("No matching students found.");
            return;
        }
        
        System.out.println("ID | Name | Roll Number | Department | Semester");
        for (Student s : students) {
            System.out.println(s.toTableRow());
        }
    }
}
