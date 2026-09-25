package com.college.management.ui;

import com.college.management.model.Course;
import com.college.management.model.Faculty;
import com.college.management.model.Student;
import com.college.management.service.CourseService;
import com.college.management.service.FacultyService;
import com.college.management.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class SearchMenu {
    private StudentService studentService;
    private CourseService courseService;
    private FacultyService facultyService;
    private Scanner scanner;

    public SearchMenu(StudentService studentService, CourseService courseService, FacultyService facultyService, Scanner scanner) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.facultyService = facultyService;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("===== SEARCH =====");
            System.out.println("1. Search Students");
            System.out.println("2. Search Courses");
            System.out.println("3. Search Faculty");
            System.out.println("4. Search All");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    searchStudents();
                    break;
                case "2":
                    searchCourses();
                    break;
                case "3":
                    searchFaculty();
                    break;
                case "4":
                    searchAll();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void searchStudents() {
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine();
        List<Student> results = studentService.searchStudents(keyword);
        
        System.out.println("Found " + results.size() + " students.");
        for (Student s : results) {
            System.out.println(s.toDisplayString());
        }
    }

    private void searchCourses() {
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine();
        List<Course> results = courseService.searchCourses(keyword);
        
        System.out.println("Found " + results.size() + " courses.");
        for (Course c : results) {
            System.out.println(c.toDisplayString());
        }
    }

    private void searchFaculty() {
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine();
        List<Faculty> results = facultyService.searchFaculty(keyword);
        
        System.out.println("Found " + results.size() + " faculty members.");
        for (Faculty f : results) {
            System.out.println(f.toDisplayString());
        }
    }

    private void searchAll() {
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine();
        
        System.out.println("--- Students ---");
        List<Student> students = studentService.searchStudents(keyword);
        for (Student s : students) System.out.println(s.getName() + " (" + s.getRollNumber() + ")");
        
        System.out.println("--- Courses ---");
        List<Course> courses = courseService.searchCourses(keyword);
        for (Course c : courses) System.out.println(c.getCourseName() + " (" + c.getCourseCode() + ")");
        
        System.out.println("--- Faculty ---");
        List<Faculty> faculty = facultyService.searchFaculty(keyword);
        for (Faculty f : faculty) System.out.println(f.getName() + " (" + f.getEmployeeId() + ")");
    }
}
