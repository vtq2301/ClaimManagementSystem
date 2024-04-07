/**
 *@author Vu Tien Quang - s3981278
 */
package main.java.com.FP.insurance.service;

import main.java.com.FP.insurance.model.Claim;

import java.util.List;

public interface ProcessManager<T> {
    void add(T newData);
    void update(String id, T updatedData);
    String delete(String id);
    T getOne(String id);
    List<T> getAll();
    void viewOne(String id);
    void viewAll();

}
