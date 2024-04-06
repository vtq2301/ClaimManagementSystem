package main.java.com.FP.insurance.dao;

import java.util.List;

public interface DAO<T>{
    List<T> readAll();
    void writeAll(List<T> data);
}
