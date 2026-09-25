package com.college.management.service;

import com.college.management.model.Course;
import com.college.management.model.Result;
import com.college.management.repository.CourseRepository;
import com.college.management.repository.ResultRepository;
import com.college.management.repository.StudentRepository;
import com.college.management.util.FileStorageUtil;
import com.college.management.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ReportService {
    private ResultRepository resultRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public ReportService(ResultRepository resultRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    // group by dept
    public Map<String, List<Result>> getDepartmentSummary() {
        Map<String, List<Result>> summary = new TreeMap<>();
        List<Result> allResults = resultRepository.findAll();
        
        for (Result result : allResults) {
            String dept = result.getStudent().getDepartment();
            summary.computeIfAbsent(dept, k -> new ArrayList<>()).add(result);
        }
        
        return summary;
    }

    public Pair<Integer, Integer> getPassFailStats(String courseCode) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        List<Result> results = resultRepository.findByCourse(course.getId());
        int passCount = 0;
        int failCount = 0;
        
        for (Result result : results) {
            if (result.isPassed()) {
                passCount++;
            } else {
                failCount++;
            }
        }
        
        return new Pair<>(passCount, failCount);
    }

    public void exportResultsToCsv(String courseCode, String filePath) {
        Course course = courseRepository.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found: " + courseCode));
        
        List<Result> results = resultRepository.findByCourse(course.getId());
        if (results.isEmpty()) return;
        
        String[] headers = results.get(0).getCsvHeaders();
        List<String[]> data = new ArrayList<>();
        
        for (Result result : results) {
            data.add(result.toCsvRow().split(","));
        }
        
        FileStorageUtil.writeToCsv(filePath, headers, data);
    }

    public void printDepartmentReport() {
        Map<String, List<Result>> summary = getDepartmentSummary();
        
        for (Map.Entry<String, List<Result>> entry : summary.entrySet()) {
            String dept = entry.getKey();
            List<Result> results = entry.getValue();
            
            double totalPercent = 0;
            for (Result r : results) {
                totalPercent += r.getTotalPercentage();
            }
            double avg = results.isEmpty() ? 0 : totalPercent / results.size();
            
            System.out.println("Department: " + dept + " | Students: " + results.size() + " | Avg Percentage: " + String.format("%.2f", avg));
        }
    }
}
