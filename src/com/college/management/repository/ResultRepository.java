package com.college.management.repository;

import com.college.management.model.Result;
import java.util.*;
import java.util.stream.Collectors;

// treeset so results stay sorted
public class ResultRepository {
    private TreeSet<Result> results = new TreeSet<>();

    public void add(Result result) { results.add(result); }
    public List<Result> findByStudent(int studentId) {
        return results.stream().filter(r -> r.getStudent().getId() == studentId).collect(Collectors.toList());
    }
    public List<Result> findByCourse(int courseId) {
        return results.stream().filter(r -> r.getCourse().getId() == courseId).collect(Collectors.toList());
    }
    
    public Optional<Result> findByStudentAndCourse(int studentId, int courseId) {
        return results.stream()
                .filter(r -> r.getStudent().getId() == studentId && r.getCourse().getId() == courseId)
                .findFirst();
    }
    public List<Result> findAll() { return new ArrayList<>(results); }
    public List<Result> getTopN(int n) {
        List<Result> top = new ArrayList<>();
        int i = 0;
        for (Result r : results) {
            if (i >= n) break;
            top.add(r);
            i++;
        }
        return top;
    }
    
    public int count() { return results.size(); }
    
    public void clear() { results.clear(); }
}
