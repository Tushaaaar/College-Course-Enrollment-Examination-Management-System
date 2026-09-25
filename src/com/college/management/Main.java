package com.college.management;

import com.college.management.model.Course;
import com.college.management.model.CourseAssignment;
import com.college.management.model.Enrollment;
import com.college.management.model.Exam;
import com.college.management.model.Faculty;
import com.college.management.model.Mark;
import com.college.management.model.Result;
import com.college.management.model.Student;
import com.college.management.repository.CourseAssignmentRepository;
import com.college.management.repository.CourseRepository;
import com.college.management.repository.EnrollmentRepository;
import com.college.management.repository.ExamRepository;
import com.college.management.repository.FacultyRepository;
import com.college.management.repository.MarkRepository;
import com.college.management.repository.ResultRepository;
import com.college.management.repository.StudentRepository;
import com.college.management.service.CourseService;
import com.college.management.service.EnrollmentService;
import com.college.management.service.ExamService;
import com.college.management.service.FacultyService;
import com.college.management.service.MarkService;
import com.college.management.service.ReportService;
import com.college.management.service.ResultService;
import com.college.management.service.StudentService;
import com.college.management.ui.CourseMenu;
import com.college.management.ui.EnrollmentMenu;
import com.college.management.ui.ExamMenu;
import com.college.management.ui.FacultyMenu;
import com.college.management.ui.MainMenu;
import com.college.management.ui.ResultMenu;
import com.college.management.ui.SearchMenu;
import com.college.management.ui.StudentMenu;
import com.college.management.util.FileStorageUtil;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static StudentRepository studentRepo;
    private static CourseRepository courseRepo;
    private static FacultyRepository facultyRepo;
    private static EnrollmentRepository enrollmentRepo;
    private static ExamRepository examRepo;
    private static MarkRepository markRepo;
    private static ResultRepository resultRepo;
    private static CourseAssignmentRepository assignmentRepo;

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println("  COLLEGE COURSE & EXAMINATION");
        System.out.println("  MANAGEMENT SYSTEM - INITIALIZING");
        System.out.println("========================================");

        // make data folder if not there
        new File("data").mkdirs();

        // init repos
        studentRepo = new StudentRepository();
        courseRepo = new CourseRepository();
        facultyRepo = new FacultyRepository();
        enrollmentRepo = new EnrollmentRepository();
        examRepo = new ExamRepository();
        markRepo = new MarkRepository();
        resultRepo = new ResultRepository();
        assignmentRepo = new CourseAssignmentRepository();

        loadData();

        // setting up services
        StudentService studentService = new StudentService(studentRepo);
        CourseService courseService = new CourseService(courseRepo);
        FacultyService facultyService = new FacultyService(facultyRepo, assignmentRepo, courseRepo);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepo, studentRepo, courseRepo);
        ExamService examService = new ExamService(examRepo, courseRepo);
        MarkService markService = new MarkService(markRepo, examRepo, studentRepo, enrollmentRepo);
        ResultService resultService = new ResultService(resultRepo, markRepo, enrollmentRepo, studentRepo, courseRepo, examRepo);
        ReportService reportService = new ReportService(resultRepo, studentRepo, courseRepo);

        // menus
        Scanner scanner = new Scanner(System.in);
        StudentMenu studentMenu = new StudentMenu(studentService, scanner);
        CourseMenu courseMenu = new CourseMenu(courseService, scanner);
        FacultyMenu facultyMenu = new FacultyMenu(facultyService, scanner);
        EnrollmentMenu enrollmentMenu = new EnrollmentMenu(enrollmentService, scanner);
        ExamMenu examMenu = new ExamMenu(examService, markService, enrollmentService, scanner);
        ResultMenu resultMenu = new ResultMenu(resultService, reportService, scanner);
        SearchMenu searchMenu = new SearchMenu(studentService, courseService, facultyService, scanner);

        MainMenu mainMenu = new MainMenu(studentMenu, courseMenu, facultyMenu, enrollmentMenu, examMenu, resultMenu, searchMenu, scanner);

        // auto save if something goes wrong
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveData));

        mainMenu.show();

        saveData();
        System.out.println("All data saved successfully.");
        scanner.close();
    }

    private static void loadData() {
        try {
            List<Student> students = FileStorageUtil.loadStudents("data/students.csv");
            int maxStudentId = 0;
            for (Student s : students) {
                studentRepo.add(s);
                if (s.getId() > maxStudentId) maxStudentId = s.getId();
            }
            StudentService.setNextId(maxStudentId + 1);

            List<Course> courses = FileStorageUtil.loadCourses("data/courses.csv");
            int maxCourseId = 0;
            for (Course c : courses) {
                courseRepo.add(c);
                if (c.getId() > maxCourseId) maxCourseId = c.getId();
            }
            CourseService.setNextId(maxCourseId + 1);

            List<Faculty> faculty = FileStorageUtil.loadFaculty("data/faculty.csv");
            int maxFacultyId = 0;
            for (Faculty f : faculty) {
                facultyRepo.add(f);
                if (f.getId() > maxFacultyId) maxFacultyId = f.getId();
            }
            FacultyService.setNextId(maxFacultyId + 1);

            List<Enrollment> enrollments = FileStorageUtil.loadEnrollments("data/enrollments.csv", studentRepo, courseRepo);
            int maxEnrollId = 0;
            for (Enrollment e : enrollments) {
                enrollmentRepo.add(e);
                if (e.getId() > maxEnrollId) maxEnrollId = e.getId();
            }
            EnrollmentService.setNextId(maxEnrollId + 1);

            List<Exam> exams = FileStorageUtil.loadExams("data/exams.csv", courseRepo);
            int maxExamId = 0;
            for (Exam e : exams) {
                examRepo.add(e);
                if (e.getExamId() > maxExamId) maxExamId = e.getExamId();
            }
            ExamService.setNextId(maxExamId + 1);

            List<Mark> marks = FileStorageUtil.loadMarks("data/marks.csv", examRepo, studentRepo);
            int maxMarkId = 0;
            for (Mark m : marks) {
                markRepo.add(m);
                if (m.getId() > maxMarkId) maxMarkId = m.getId();
            }
            MarkService.setNextId(maxMarkId + 1);

            List<Result> results = FileStorageUtil.loadResults("data/results.csv", studentRepo, courseRepo);
            int maxResultId = 0;
            for (Result r : results) {
                resultRepo.add(r);
                if (r.getId() > maxResultId) maxResultId = r.getId();
            }
            ResultService.setNextId(maxResultId + 1);

        } catch (Exception e) {
            System.out.println("Warning: Could not load some data files. They will be created upon saving. " + e.getMessage());
        }
    }

    private static void saveData() {
        try {
            // saving all data to files
            FileStorageUtil.saveStudents(studentRepo.findAll(), "data/students.csv");
            FileStorageUtil.saveCourses(courseRepo.findAll(), "data/courses.csv");
            FileStorageUtil.saveFaculty(facultyRepo.findAll(), "data/faculty.csv");
            FileStorageUtil.saveEnrollments(enrollmentRepo.findAll(), "data/enrollments.csv");
            FileStorageUtil.saveExams(examRepo.findAll(), "data/exams.csv");
            FileStorageUtil.saveMarks(markRepo.findAll(), "data/marks.csv");
            FileStorageUtil.saveResults(resultRepo.findAll(), "data/results.csv");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
