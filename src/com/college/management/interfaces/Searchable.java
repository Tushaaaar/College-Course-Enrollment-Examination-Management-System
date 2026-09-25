package com.college.management.interfaces;
public interface Searchable<T> {
    boolean matches(String keyword);
    String getSearchKey();
}
