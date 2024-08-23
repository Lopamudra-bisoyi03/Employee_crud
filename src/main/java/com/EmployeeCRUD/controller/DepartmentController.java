package com.EmployeeCRUD.controller;

import com.EmployeeCRUD.models.Department;
import com.EmployeeCRUD.repositories.impl.FileEmployeeRepository;
import com.EmployeeCRUD.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DepartmentController {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    public void addDepartment(String departmentName) {
        Department department = new Department( departmentName);
        departmentService.addDepartment(department);
        logger.debug("Department added successfully");
    }

    public Department getDepartmentById(int id) {
        return departmentService.getDepartmentById(id);
    }


    public void updateDepartment(int id, String departmentName) {
        Department department = new Department(id, departmentName);
        departmentService.updateDepartment(department);
        logger.debug("Department updated successfully");
    }

    public void deleteDepartment(int id) {
        departmentService.deleteDepartment(id);
        logger.debug("Department deleted successfully");
    }

}
