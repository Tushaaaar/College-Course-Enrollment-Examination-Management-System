package com.college.management.ui;

import com.college.management.model.Course;
import com.college.management.model.enums.CourseType;
import com.college.management.service.CourseService;

import java.util.List;
import java.util.Scanner;

public class CourseMenu {
    private CourseService courseService;
    private Scanner scanner;

    public CourseMenu(CourseService courseService, Scanner scanner) {
        this.courseService = courseService;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("===== COURSE MANAGEMENT =====");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Delete Course");
            System.out.println("4. View Course Details");
            System.out.println("5. List All Courses");
            System.out.println("6. Search Courses");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addCourse();
                    break;
                case "2":
                    updateCourse();
                    break;
                case "3":
                    deleteCourse();
                    break;
                case "4":
                    viewCourse();
                    break;
                case "5":
                    listAllCourses();
                    break;
                case "6":
                    searchCourses();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addCourse() {
        try {
            System.out.print("Course Code: ");
            String code = scanner.nextLine();
            System.out.print("Course Name: ");
            String name = scanner.nextLine();
            System.out.print("Credits: ");
            int credits = Integer.parseInt(scanner.nextLine());
            System.out.print("Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Course Type (1=CORE, 2=ELECTIVE, 3=LAB): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());
            CourseType type = CourseType.CORE;
            if (typeChoice == 2) type = CourseType.ELECTIVE;
            else if (typeChoice == 3) type = CourseType.LAB;
            
            System.out.print("Department: ");
            String department = scanner.nextLine();
            
            courseService.addCourse(code, name, credits, capacity, type, department);
            System.out.println("Course added successfully.");
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateCourse() {
        try {
            System.out.print("Enter Course Code to update: ");
            String code = scanner.nextLine();
            System.out.print("New Course Name: ");
            String name = scanner.nextLine();
            System.out.print("New Credits: ");
            int credits = Integer.parseInt(scanner.nextLine());
            System.out.print("New Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine());
            
            System.out.print("New Course Type (1=CORE, 2=ELECTIVE, 3=LAB): ");
            int typeChoice = Integer.parseInt(scanner.nextLine());
            CourseType type = CourseType.CORE;
            if (typeChoice == 2) type = CourseType.ELECTIVE;
            else if (typeChoice == 3) type = CourseType.LAB;
            
            System.out.print("New Department: ");
            String department = scanner.nextLine();
            
            courseService.updateCourse(code, name, credits, capacity, type, department);
            System.out.println("Course updated successfully.");
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        try {
            System.out.print("Enter Course Code to delete: ");
            String code = scanner.nextLine();
            courseService.deleteCourse(code);
            System.out.println("Course deleted successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewCourse() {
        try {
            System.out.print("Enter Course Code: ");
            String code = scanner.nextLine();
            Course course = courseService.getCourse(code);
            System.out.println(course.toDisplayString());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        System.out.println("ID | Code | Name | Credits | Type | Department");
        for (Course c : courses) {
            System.out.println(c.toTableRow());
        }
    }

    private void searchCourses() {
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        List<Course> courses = courseService.searchCourses(keyword);
        if (courses.isEmpty()) {
            System.out.println("No matching courses found.");
            return;
        }
        
        System.out.println("ID | Code | Name | Credits | Type | Department");
        for (Course c : courses) {
            System.out.println(c.toTableRow());
        }
    }
}
