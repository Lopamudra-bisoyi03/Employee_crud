package com.EmployeeCRUD.repositories;

import com.EmployeeCRUD.models.Employee;

import java.util.List;

public interface EmployeeRepository {
    void add(Employee employee);
    Employee getById(int id);
    Employee getByName(String name);
    List<Employee> getAll();
    void update(Employee employee);
    void delete(int id);

    Employee getByEmail(String email);
}
