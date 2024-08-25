package org.example.repository;

import java.util.List;

public interface Crud<T> {

    List<T>findAll();

    T findById(int id);

    void create(T obj);

    void update(T obj);

    void delete(int id);

}