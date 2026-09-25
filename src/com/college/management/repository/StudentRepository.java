package com.college.management.repository;

import com.college.management.model.Student;
import java.util.*;
import java.util.stream.Collectors;
public class StudentRepository extends BaseRepository<Student> {

    @Override
    public void update(Student student) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == student.getId()) {
                Student s = items.get(i);
                s.setName(student.getName());
                s.setEmail(student.getEmail());
                s.setPhone(student.getPhone());
                s.setDepartment(student.getDepartment());
                s.setSemester(student.getSemester());
                s.setActive(student.isActive());
                break;
            }
        }
    }

    @Override
    public Optional<Student> findById(int id) {
        return items.stream().filter(s -> s.getId() == id).findFirst();
    }

    public Optional<Student> findByRollNumber(String rollNumber) {
        return items.stream().filter(s -> s.getRollNumber().equals(rollNumber)).findFirst();
    }
    public List<Student> findByDepartment(String department) {
        return items.stream().filter(s -> s.getDepartment().equalsIgnoreCase(department)).collect(Collectors.toList());
    }
}
