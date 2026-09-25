package com.college.management.ui;

import com.college.management.model.CourseAssignment;
import com.college.management.model.Faculty;
import com.college.management.service.FacultyService;
import com.college.management.util.TableFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FacultyMenu {
    private FacultyService facultyService;
    private Scanner scanner;

    public FacultyMenu(FacultyService facultyService, Scanner scanner) {
        this.facultyService = facultyService;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("===== FACULTY MANAGEMENT =====");
            System.out.println("1. Add Faculty");
            System.out.println("2. Update Faculty");
            System.out.println("3. Delete Faculty");
            System.out.println("4. View Faculty Profile");
            System.out.println("5. Assign Faculty to Course");
            System.out.println("6. Remove Faculty from Course");
            System.out.println("7. List All Faculty");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addFaculty();
                    break;
                case "2":
                    updateFaculty();
                    break;
                case "3":
                    deleteFaculty();
                    break;
                case "4":
                    viewFaculty();
                    break;
                case "5":
                    assignToCourse();
                    break;
                case "6":
                    removeFromCourse();
                    break;
                case "7":
                    listAllFaculty();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addFaculty() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Designation: ");
            String designation = scanner.nextLine();
            System.out.print("Specialization: ");
            String specialization = scanner.nextLine();
            
            facultyService.addFaculty(name, email, phone, employeeId, designation, specialization);
            System.out.println("Faculty added successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateFaculty() {
        try {
            System.out.print("Enter Employee ID to update: ");
            String employeeId = scanner.nextLine();
            System.out.print("New Name: ");
            String name = scanner.nextLine();
            System.out.print("New Email: ");
            String email = scanner.nextLine();
            System.out.print("New Phone: ");
            String phone = scanner.nextLine();
            System.out.print("New Designation: ");
            String designation = scanner.nextLine();
            System.out.print("New Specialization: ");
            String specialization = scanner.nextLine();
            
            facultyService.updateFaculty(employeeId, name, email, phone, designation, specialization);
            System.out.println("Faculty updated successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteFaculty() {
        try {
            System.out.print("Enter Employee ID to delete: ");
            String employeeId = scanner.nextLine();
            facultyService.deleteFaculty(employeeId);
            System.out.println("Faculty deleted successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewFaculty() {
        try {
            System.out.print("Enter Employee ID: ");
            String employeeId = scanner.nextLine();
            Faculty faculty = facultyService.getFaculty(employeeId);
            System.out.println(faculty.toDisplayString());
            
            List<CourseAssignment> assignments = facultyService.getFacultyAssignments(employeeId);
            if (!assignments.isEmpty()) {
                System.out.println("Assigned Courses:");
                for (CourseAssignment a : assignments) {
                    System.out.println("- " + a.getCourse().getCourseCode() + " (" + a.getAcademicYear() + ")");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void assignToCourse() {
        try {
            System.out.print("Employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            System.out.print("Academic Year: ");
            String year = scanner.nextLine();
            
            facultyService.assignToCourse(employeeId, courseCode, year);
            System.out.println("Assigned successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void removeFromCourse() {
        try {
            System.out.print("Employee ID: ");
            String employeeId = scanner.nextLine();
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            
            facultyService.removeFromCourse(employeeId, courseCode);
            System.out.println("Removed successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAllFaculty() {
        List<Faculty> faculties = facultyService.getAllFaculty();
        if (faculties.isEmpty()) {
            System.out.println("No faculty found.");
            return;
        }
        
        String[] headers = {"ID", "Name", "Employee ID", "Designation", "Specialization"};
        List<String[]> data = new ArrayList<>();
        for (Faculty f : faculties) {
            data.add(new String[]{String.valueOf(f.getId()), f.getName(), f.getEmployeeId(), f.getDesignation(), f.getSpecialization()});
        }
        TableFormatter.printTable(headers, data);
    }
}
