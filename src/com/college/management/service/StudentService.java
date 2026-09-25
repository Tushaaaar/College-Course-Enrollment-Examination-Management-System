package com.college.management.service;

import com.college.management.exception.StudentNotFoundException;
import com.college.management.model.Student;
import com.college.management.repository.StudentRepository;
import com.college.management.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

// student stuff
public class StudentService {
    private StudentRepository studentRepository;
    private static int nextId = 1;

    public StudentService(StudentRepository repo) {
        this.studentRepository = repo;
    }

    public Student registerStudent(String name, String email, String phone, String rollNumber, String department, int semester) {
        if (!ValidationUtil.isNonEmpty(name) || !ValidationUtil.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid name or email");
        }
        Student student = new Student(nextId++, name, email, phone, rollNumber, department, semester);
        studentRepository.add(student);
        return student;
    }

    public void updateStudent(String rollNumber, String name, String email, String phone, String department, int semester) {
        Student student = getStudent(rollNumber);
        student.setName(name);
        student.setEmail(email);
        student.setPhone(phone);
        student.setDepartment(department);
        student.setSemester(semester);
        studentRepository.update(student);
    }

    public void deleteStudent(String rollNumber) {
        Student student = getStudent(rollNumber);
        student.setActive(false);
        studentRepository.update(student);
    }

    public Student getStudent(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
    }

    public List<Student> getAllStudents() {
        List<Student> active = new ArrayList<>();
        for (Student s : studentRepository.findAll()) {
            if (s.isActive()) active.add(s);
        }
        return active;
    }

    public List<Student> searchStudents(String keyword) {
        List<Student> found = new ArrayList<>();
        for (Student s : studentRepository.findAll()) {
            if (s.matches(keyword)) found.add(s);
        }
        return found;
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
