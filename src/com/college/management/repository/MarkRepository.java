package com.college.management.repository;

import com.college.management.model.Mark;
import java.util.*;
import java.util.stream.Collectors;
public class MarkRepository {
    private List<Mark> marks = new ArrayList<>();

    public void add(Mark mark) { marks.add(mark); }
    public List<Mark> findByExam(int examId) {
        return marks.stream().filter(m -> m.getExam().getExamId() == examId).collect(Collectors.toList());
    }
    public List<Mark> findByStudent(int studentId) {
        return marks.stream().filter(m -> m.getStudent().getId() == studentId).collect(Collectors.toList());
    }
    
    public Optional<Mark> findByExamAndStudent(int examId, int studentId) {
        return marks.stream()
                .filter(m -> m.getExam().getExamId() == examId && m.getStudent().getId() == studentId)
                .findFirst();
    }
    
    public void update(Mark mark) {
        for (Mark m : marks) {
            if (m.getId() == mark.getId()) {
                m.setMarksObtained(mark.getMarksObtained());
                break;
            }
        }
    }
    public List<Mark> findAll() { return new ArrayList<>(marks); }
    public int count() { return marks.size(); }
    public void clear() { marks.clear(); }
}
