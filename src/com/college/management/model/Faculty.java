package com.college.management.model;

import com.college.management.interfaces.Displayable;
import com.college.management.interfaces.Searchable;
import java.util.Objects;
public class Faculty extends Person implements Searchable<Faculty>, Displayable {
    private String employeeId;
    private String designation;
    private String specialization;
    private boolean isActive = true;

    public Faculty(int id, String name, String email, String phone, String employeeId, String designation, String specialization) {
        super(id, name, email, phone);
        this.employeeId = employeeId;
        this.designation = designation;
        this.specialization = specialization;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String getRole() {
        return "Faculty";
    }

    @Override
    public String getDetails() {
        return "Employee ID: " + employeeId + "\n" +
               "Name: " + getName() + "\n" +
               "Designation: " + designation + "\n" +
               "Specialization: " + specialization + "\n" +
               "Email: " + getEmail() + "\n" +
               "Phone: " + getPhone() + "\n" +
               "Active: " + isActive;
    }

    @Override
    public boolean matches(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return getName().toLowerCase().contains(lowerKeyword) ||
               employeeId.toLowerCase().contains(lowerKeyword) ||
               designation.toLowerCase().contains(lowerKeyword);
    }

    @Override
    public String getSearchKey() {
        return employeeId;
    }

    @Override
    public String toDisplayString() {
        return getDetails();
    }

    @Override
    public String toTableRow() {
        return String.format("| %-12s | %-20s | %-15s | %-20s |", employeeId, getName(), designation, specialization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(employeeId, faculty.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}
