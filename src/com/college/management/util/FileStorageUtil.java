package com.college.management.util;

import com.college.management.model.*;
import com.college.management.model.enums.*;
import com.college.management.repository.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

// csv file stuff
public class FileStorageUtil {

    public static void writeToCsv(String filePath, String[] headers, List<String[]> rows) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(String.join(",", headers));
            bw.newLine();
            for (String[] row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + filePath);
        }
    }

    public static List<String[]> readFromCsv(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = // skip header row
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    data.add(line.split(","));
                }
            }
        } catch (FileNotFoundException e) {
            // File does not exist yet, return empty list
        } catch (IOException e) {
            System.err.println("Error reading from CSV file: " + filePath);
        }
        return data;
    }
    public static void saveStudents(List<Student> students, String filePath) {
        String[] headers = {"ID", "RollNumber", "Name", "Email", "Phone", "Department", "Semester", "IsActive"};
        List<String[]> rows = new ArrayList<>();
        for (Student s : students) {
            rows.add(new String[]{
                String.valueOf(s.getId()), s.getRollNumber(), s.getName(), s.getEmail(), s.getPhone(),
                s.getDepartment(), String.valueOf(s.getSemester()), String.valueOf(s.isActive())
            });
        }
        writeToCsv(filePath, headers, rows);
    }

    public static List<Student> loadStudents(String filePath) {
        List<Student> students = new ArrayList<>();
        List<String[]> rows = readFromCsv(filePath);
        for (String[] row : rows) {
            try {
                Student s = new Student(Integer.parseInt(row[0]), row[2], row[3], row[4], row[1], row[5], Integer.parseInt(row[6]));
                s.setActive(Boolean.parseBoolean(row[7]));
                students.add(s);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.err.println("Error parsing student row");
            }
        }
        return students;
    }

    public static void saveCourses(List<Course> courses, String filePath) {
        String[] headers = {"ID", "CourseCode", "CourseName", "Credits", "MaxCapacity", "CurrentEnrollment", "CourseType", "Department"};
        List<String[]> rows = new ArrayList<>();
        for (Course c : courses) {
            rows.add(new String[]{
                String.valueOf(c.getId()), c.getCourseCode(), c.getCourseName(), String.valueOf(c.getCredits()),
                String.valueOf(c.getMaxCapacity()), String.valueOf(c.getCurrentEnrollment()), c.getType().name(), c.getDepartment()
            });
        }
        writeToCsv(filePath, headers, rows);
    }

    public static List<Course> loadCourses(String filePath) {
        List<Course> courses = new ArrayList<>();
        List<String[]> rows = readFromCsv(filePath);
        for (String[] row : rows) {
            try {
                Course c = new Course(Integer.parseInt(row[0]), row[1], row[2], Integer.parseInt(row[3]), Integer.parseInt(row[4]), CourseType.valueOf(row[6]), row[7]);
                for (int i = 0; i < Integer.parseInt(row[5]); i++) {
                    c.incrementEnrollment();
                }
                courses.add(c);
            } catch (Exception e) {
                System.err.println("Error parsing course row");
            }
        }
        return courses;
    }

    public static void saveFaculty(List<Faculty> faculty, String filePath) {
        String[] headers = {"ID", "EmployeeId", "Name", "Email", "Phone", "Designation", "Specialization", "IsActive"};
        List<String[]> rows = new ArrayList<>();
        for (Faculty f : faculty) {
            rows.add(new String[]{
                String.valueOf(f.getId()), f.getEmployeeId(), f.getName(), f.getEmail(), f.getPhone(),
                f.getDesignation(), f.getSpecialization(), String.valueOf(f.isActive())
            });
        }
        writeToCsv(filePath, headers, rows);
    }

    public static List<Faculty> loadFaculty(String filePath) {
        List<Faculty> faculty = new ArrayList<>();
        List<String[]> rows = readFromCsv(filePath);
        for (String[] row : rows) {
            try {
                Faculty f = new Faculty(Integer.parseInt(row[0]), row[2], row[3], row[4], row[1], row[5], row[6]);
                f.setActive(Boolean.parseBoolean(row[7]));
                faculty.add(f);
            } catch (Exception e) {
                System.err.println("Error parsing faculty row");
            }
        }
        return faculty;
    }

    public static void saveEnrollments(List<Enrollment> enrollments, String filePath) {
        String[] headers = {"ID", "StudentRoll", "CourseCode", "EnrollmentDate", "Status"};
        List<String[]> rows = new ArrayList<>();
        for (Enrollment e : enrollments) {
            rows.add(new String[]{
                String.valueOf(e.getId()), e.getStudent().getRollNumber(), e.getCourse().getCourseCode(),
                e.getEnrollmentDate().toString(), e.getStatus().name()
            });
        }
        writeToCsv(filePath, headers, rows);
    }

    public static List<Enrollment> loadEnrollments(String filePath, StudentRepository studentRepo, CourseRepository courseRepo) {
        List<Enrollment> enrollments = new ArrayList<>();
        List<String[]> rows = readFromCsv(filePath);
        for (String[] row : rows) {
            try {
                Optional<Student> studentOpt = studentRepo.findByRollNumber(row[1]);
                Optional<Course> courseOpt = courseRepo.findByCode(row[2]);
                if (studentOpt.isPresent() && courseOpt.isPresent()) {
                    Enrollment e = new Enrollment(Integer.parseInt(row[0]), studentOpt.get(), courseOpt.get());
                    e.setEnrollmentDate(LocalDate.parse(row[3]));
                    e.setStatus(EnrollmentStatus.valueOf(row[4]));
                    enrollments.add(e);
                } else {
                    System.err.println("Could not resolve student or course for enrollment row");
                }
            } catch (Exception e) {
                System.err.println("Error parsing enrollment row");
            }
        }
        return enrollments;
    }

    public static void saveMarks(List<Mark> marks, String filePath) {
        String[] headers = {"ID", "ExamId", "StudentRoll", "MarksObtained"};
        List<String[]> rows = new ArrayList<>();
        for (Mark m : marks) {
            rows.add(new String[]{
                String.valueOf(m.getId()), String.valueOf(m.getExam().getExamId()), m.getStudent().getRollNumber(), String.valueOf(m.getMarksObtained())
            });
        }
        writeToCsv(filePath, headers, rows);
    }

    public static List<Mark> loadMarks(String filePath, ExamRepository examRepo, StudentRepository studentRepo) {
        List<Mark> marks = new ArrayList<>();
        List<String[]> rows = readFromCsv(filePath);
        for (String[] row : rows) {
            try {
                Optional<Exam> examOpt = examRepo.findById(Integer.parseInt(row[1]));
                Optional<Student> studentOpt = studentRepo.findByRollNumber(row[2]);
                if (examOpt.isPresent() && studentOpt.isPresent()) {
                    Mark m = new Mark(Integer.parseInt(row[0]), examOpt.get(), studentOpt.get(), Double.parseDouble(row[3]));
                    marks.add(m);
                } else {
                    System.err.println("Could not resolve exam or student for mark row");
                }
            } catch (Exception e) {
                System.err.println("Error parsing mark row");
            }
        }
        return marks;
    }

    public static void saveExams(List<Exam> exams, String filePath) {
        String[] headers = {"ID", "CourseCode", "ExamType", "ExamDate", "TotalMarks"};
        List<String[]> rows = new ArrayList<>();
        for (Exam e : exams) {
            rows.add(new String[]{
                String.valueOf(e.getExamId()), e.getCourse().getCourseCode(), e.getType().name(), e.getExamDate().toString(), String.valueOf(e.getTotalMarks())
            });
        }
        writeToCsv(filePath, headers, rows);
    }

    public static List<Exam> loadExams(String filePath, CourseRepository courseRepo) {
        List<Exam> exams = new ArrayList<>();
        List<String[]> rows = readFromCsv(filePath);
        for (String[] row : rows) {
            try {
                Optional<Course> courseOpt = courseRepo.findByCode(row[1]);
                if (courseOpt.isPresent()) {
                    Exam e = new Exam(Integer.parseInt(row[0]), courseOpt.get(), ExamType.valueOf(row[2]), LocalDate.parse(row[3]), Integer.parseInt(row[4]));
                    exams.add(e);
                } else {
                    System.err.println("Could not resolve course for exam row");
                }
            } catch (Exception e) {
                System.err.println("Error parsing exam row");
            }
        }
        return exams;
    }

    public static void saveResults(List<Result> results, String filePath) {
        String[] headers = {"ID", "StudentRoll", "CourseCode", "Percentage", "Grade", "Passed"};
        List<String[]> rows = new ArrayList<>();
        for (Result r : results) {
            rows.add(new String[]{
                String.valueOf(r.getId()), r.getStudent().getRollNumber(), r.getCourse().getCourseCode(),
                String.valueOf(r.getTotalPercentage()), String.valueOf(r.getGrade()), String.valueOf(r.isPassed())
            });
        }
        writeToCsv(filePath, headers, rows);
    }

    public static List<Result> loadResults(String filePath, StudentRepository studentRepo, CourseRepository courseRepo) {
        List<Result> results = new ArrayList<>();
        List<String[]> rows = readFromCsv(filePath);
        for (String[] row : rows) {
            try {
                Optional<Student> studentOpt = studentRepo.findByRollNumber(row[1]);
                Optional<Course> courseOpt = courseRepo.findByCode(row[2]);
                if (studentOpt.isPresent() && courseOpt.isPresent()) {
                    Result r = new Result(Integer.parseInt(row[0]), studentOpt.get(), courseOpt.get());
                    r.setTotalPercentage(Double.parseDouble(row[3]));
                    r.setGrade(row[4].charAt(0));
                    r.setPassed(Boolean.parseBoolean(row[5]));
                    results.add(r);
                } else {
                    System.err.println("Could not resolve student or course for result row");
                }
            } catch (Exception e) {
                System.err.println("Error parsing result row");
            }
        }
        return results;
    }
}
