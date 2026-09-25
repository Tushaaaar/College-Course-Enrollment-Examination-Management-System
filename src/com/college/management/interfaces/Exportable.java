package com.college.management.interfaces;
public interface Exportable {
    String toCsvRow();
    String[] getCsvHeaders();
}
