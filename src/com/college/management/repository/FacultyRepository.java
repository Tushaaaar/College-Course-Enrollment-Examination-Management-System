package com.college.management.repository;

import com.college.management.model.Faculty;
import java.util.*;
import java.util.stream.Collectors;
public class FacultyRepository extends BaseRepository<Faculty> {

    @Override
    public void update(Faculty faculty) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId() == faculty.getId()) {
                Faculty f = items.get(i);
                f.setName(faculty.getName());
                f.setEmail(faculty.getEmail());
                f.setPhone(faculty.getPhone());
                f.setEmployeeId(faculty.getEmployeeId());
                f.setDesignation(faculty.getDesignation());
                f.setSpecialization(faculty.getSpecialization());
                f.setActive(faculty.isActive());
                break;
            }
        }
    }

    @Override
    public Optional<Faculty> findById(int id) {
        return items.stream().filter(f -> f.getId() == id).findFirst();
    }

    public Optional<Faculty> findByEmployeeId(String empId) {
        return items.stream().filter(f -> f.getEmployeeId().equals(empId)).findFirst();
    }
    public List<Faculty> findByDesignation(String designation) {
        return items.stream().filter(f -> f.getDesignation().equalsIgnoreCase(designation)).collect(Collectors.toList());
    }
}
