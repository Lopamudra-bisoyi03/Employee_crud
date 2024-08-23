package com.EmployeeCRUD.repositories;

import com.EmployeeCRUD.models.Department;

import java.util.List;

public interface DepartmentRepository {
    int add(Department department);
    Department getById(int id);
    List<Department> getAll();
    void update(Department department);
    void delete(int id);
}
