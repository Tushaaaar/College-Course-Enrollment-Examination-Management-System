package com.college.management.model;

import com.college.management.interfaces.Displayable;
import com.college.management.interfaces.Exportable;
import com.college.management.interfaces.Searchable;
import java.util.Objects;
public class Student extends Person implements Comparable<Student>, Searchable<Student>, Displayable, Exportable {
    private String rollNumber;
    private String department;
    private int semester;
    private boolean isActive = true;

    public Student(int id, String name, String email, String phone, String rollNumber, String department, int semester) {
        super(id, name, email, phone);
        this.rollNumber = rollNumber;
        this.department = department;
        this.semester = semester;
    }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String getDetails() {
        return "Roll Number: " + rollNumber + "\n" +
               "Name: " + getName() + "\n" +
               "Department: " + department + "\n" +
               "Semester: " + semester + "\n" +
               "Email: " + getEmail() + "\n" +
               "Phone: " + getPhone() + "\n" +
               "Active: " + isActive;
    }

    @Override
    public int compareTo(Student other) {
        return this.rollNumber.compareTo(other.rollNumber);
    }

    @Override
    // lowercase for searching
    public boolean matches(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return getName().toLowerCase().contains(lowerKeyword) ||
               rollNumber.toLowerCase().contains(lowerKeyword) ||
               department.toLowerCase().contains(lowerKeyword);
    }

    @Override
    public String getSearchKey() {
        return rollNumber;
    }

    @Override
    public String toDisplayString() {
        return getDetails();
    }

    @Override
    public String toTableRow() {
        return String.format("| %-12s | %-20s | %-15s | %-8d |", rollNumber, getName(), department, semester);
    }

    @Override
    public String toCsvRow() {
        return rollNumber + "," + getName() + "," + getEmail() + "," + getPhone() + "," + department + "," + semester;
    }

    @Override
    public String[] getCsvHeaders() {
        return new String[]{"Roll Number", "Name", "Email", "Phone", "Department", "Semester"};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(rollNumber, student.rollNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rollNumber);
    }
}
