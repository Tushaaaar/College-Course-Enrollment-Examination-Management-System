package com.college.management.ui;

import com.college.management.model.Result;
import com.college.management.service.ReportService;
import com.college.management.service.ResultService;
import com.college.management.util.Pair;

import java.util.List;
import java.util.Scanner;

public class ResultMenu {
    private ResultService resultService;
    private ReportService reportService;
    private Scanner scanner;

    public ResultMenu(ResultService resultService, ReportService reportService, Scanner scanner) {
        this.resultService = resultService;
        this.reportService = reportService;
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            System.out.println("===== RESULTS & REPORTS =====");
            System.out.println("1. Generate Student Result");
            System.out.println("2. Generate Course Results");
            System.out.println("3. View Student Results");
            System.out.println("4. Class Toppers");
            System.out.println("5. Department-wise Summary");
            System.out.println("6. Pass/Fail Statistics");
            System.out.println("7. Export Results to CSV");
            System.out.println("0. Back");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    generateStudentResult();
                    break;
                case "2":
                    generateCourseResults();
                    break;
                case "3":
                    viewStudentResults();
                    break;
                case "4":
                    viewClassToppers();
                    break;
                case "5":
                    departmentSummary();
                    break;
                case "6":
                    passFailStats();
                    break;
                case "7":
                    exportCsv();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void generateStudentResult() {
        try {
            System.out.print("Student Roll Number: ");
            String rollNumber = scanner.nextLine();
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            
            Result result = resultService.generateResult(rollNumber, courseCode);
            System.out.println("Result generated successfully.");
            System.out.println(result.toDisplayString());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void generateCourseResults() {
        try {
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            
            resultService.generateCourseResults(courseCode);
            System.out.println("Results generated successfully for course.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewStudentResults() {
        try {
            System.out.print("Student Roll Number: ");
            String rollNumber = scanner.nextLine();
            List<Result> results = resultService.getStudentResults(rollNumber);
            
            if (results.isEmpty()) {
                System.out.println("No results found.");
                return;
            }
            
            for (Result r : results) {
                System.out.println(r.toDisplayString());
                System.out.println("-------------------------");
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewClassToppers() {
        try {
            System.out.print("Number of toppers to display: ");
            int n = Integer.parseInt(scanner.nextLine());
            
            List<Result> toppers = resultService.getToppers(n);
            if (toppers.isEmpty()) {
                System.out.println("No results available.");
                return;
            }
            
            System.out.println("Rank | Roll Number | Name | Course | Percentage | Grade");
            int rank = 1;
            for (Result r : toppers) {
                System.out.printf("%d | %s | %s | %s | %.2f | %s%n", rank++, r.getStudent().getRollNumber(), r.getStudent().getName(), r.getCourse().getCourseCode(), r.getTotalPercentage(), String.valueOf(r.getGrade()));
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void departmentSummary() {
        try {
            reportService.printDepartmentReport();
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void passFailStats() {
        try {
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            
            Pair<Integer, Integer> stats = reportService.getPassFailStats(courseCode);
            System.out.println("Passed: " + stats.getKey());
            System.out.println("Failed: " + stats.getValue());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void exportCsv() {
        try {
            System.out.print("Course Code: ");
            String courseCode = scanner.nextLine();
            System.out.print("File Path (e.g., results_CS101.csv): ");
            String filePath = scanner.nextLine();
            
            reportService.exportResultsToCsv(courseCode, filePath);
            System.out.println("Results exported successfully.");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
