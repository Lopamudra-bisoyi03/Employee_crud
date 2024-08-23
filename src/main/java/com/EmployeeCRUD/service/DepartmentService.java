package com.EmployeeCRUD.service;

import com.EmployeeCRUD.models.Department;
import com.EmployeeCRUD.repositories.DepartmentRepository;

import java.util.List;

public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public int addDepartment(Department department) {
        return departmentRepository.add(department);
    }

    public Department getDepartmentById(int id) {
        return departmentRepository.getById(id);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.getAll();
    }

    public void updateDepartment(Department department) {
        departmentRepository.update(department);
    }

    public void deleteDepartment(int id) {
        departmentRepository.delete(id);
    }
}