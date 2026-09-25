package com.college.management.service;

import com.college.management.exception.ExamNotFoundException;
import com.college.management.exception.InvalidMarksException;
import com.college.management.exception.StudentNotFoundException;
import com.college.management.model.Exam;
import com.college.management.model.Mark;
import com.college.management.model.Student;
import com.college.management.repository.EnrollmentRepository;
import com.college.management.repository.ExamRepository;
import com.college.management.repository.MarkRepository;
import com.college.management.repository.StudentRepository;
import com.college.management.util.ValidationUtil;

import java.util.List;

public class MarkService {
    private MarkRepository markRepository;
    private ExamRepository examRepository;
    private StudentRepository studentRepository;
    private EnrollmentRepository enrollmentRepository;
    private static int nextId = 1;

    public MarkService(MarkRepository markRepository, ExamRepository examRepository, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.markRepository = markRepository;
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // check marks
    public void enterMark(int examId, String rollNumber, double marks) throws InvalidMarksException {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("Unknown", null));
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
                
        if (!ValidationUtil.isValidMarks(marks, exam.getTotalMarks())) {
            throw new InvalidMarksException(marks, exam.getTotalMarks());
        }
        
        if (enrollmentRepository.findByStudentAndCourse(student.getId(), exam.getCourse().getId()).isEmpty()) {
            throw new RuntimeException("Student not enrolled in course");
        }
        
        if (markRepository.findByExamAndStudent(examId, student.getId()).isPresent()) {
            throw new RuntimeException("Mark already entered for this exam and student");
        }
        
        Mark mark = new Mark(nextId++, exam, student, marks);
        markRepository.add(mark);
    }

    public void updateMark(int examId, String rollNumber, double newMarks) throws InvalidMarksException {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("Unknown", null));
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
        
        Mark mark = markRepository.findByExamAndStudent(examId, student.getId())
                .orElseThrow(() -> new RuntimeException("Mark not found"));
                
        if (!ValidationUtil.isValidMarks(newMarks, exam.getTotalMarks())) {
            throw new InvalidMarksException(newMarks, exam.getTotalMarks());
        }
        
        mark.setMarksObtained(newMarks);
        markRepository.update(mark);
    }

    public List<Mark> getMarksByStudent(String rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new StudentNotFoundException(rollNumber));
        return markRepository.findByStudent(student.getId());
    }

    public List<Mark> getMarksByExam(int examId) {
        return markRepository.findByExam(examId);
    }

    public static void setNextId(int id) {
        nextId = id;
    }
}
