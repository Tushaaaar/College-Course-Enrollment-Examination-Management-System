package com.college.management.repository;

import com.college.management.interfaces.Displayable;
import java.util.*;

// base class
public abstract class BaseRepository<T extends Displayable> {
    protected List<T> items = new ArrayList<>();

    public void add(T item) { items.add(item); }
    public abstract void update(T item);
    public abstract Optional<T> findById(int id);
    public boolean delete(T item) { return items.remove(item); }
    public List<T> findAll() { return new ArrayList<>(items); }
    public int count() { return items.size(); }
    public void clear() { items.clear(); }
    public void setItems(List<T> newItems) { items = new ArrayList<>(newItems); }
}
