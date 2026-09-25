package com.college.management.repository;

import com.college.management.model.Exam;
import com.college.management.model.enums.ExamType;
import java.util.*;
import java.util.stream.Collectors;
public class ExamRepository extends BaseRepository<Exam> {

    @Override
    public void update(Exam exam) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getExamId() == exam.getExamId()) {
                Exam e = items.get(i);
                e.setCourse(exam.getCourse());
                e.setType(exam.getType());
                e.setExamDate(exam.getExamDate());
                e.setTotalMarks(exam.getTotalMarks());
                e.setMarksheet(exam.getMarksheet());
                break;
            }
        }
    }

    @Override
    public Optional<Exam> findById(int id) {
        return items.stream().filter(e -> e.getExamId() == id).findFirst();
    }
    public List<Exam> findByCourse(int courseId) {
        return items.stream().filter(e -> e.getCourse().getId() == courseId).collect(Collectors.toList());
    }

    public Optional<Exam> findByCourseAndType(int courseId, ExamType type) {
        return items.stream()
                .filter(e -> e.getCourse().getId() == courseId && e.getType() == type)
                .findFirst();
    }
}
