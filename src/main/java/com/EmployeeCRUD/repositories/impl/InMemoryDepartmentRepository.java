package com.EmployeeCRUD.repositories.impl;

import com.EmployeeCRUD.models.Department;
import com.EmployeeCRUD.repositories.DepartmentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDepartmentRepository implements DepartmentRepository {
    private static final Map<Integer, Department> departmentMap = new HashMap<>();
    private static int idCounter = 1;

    @Override
    public int add(Department department) {
        department.setDepartmentId(idCounter++);
        departmentMap.put(department.getDepartmentId(), department);
        return department.getDepartmentId();
    }

    @Override
    public Department getById(int id) {
        return departmentMap.get(id);
    }

    @Override
    public List<Department> getAll() {
        return new ArrayList<>(departmentMap.values());
    }

    @Override
    public void update(Department department) {
        if (departmentMap.containsKey(department.getDepartmentId())) {
            departmentMap.put(department.getDepartmentId(), department);
        }
    }

    @Override
    public void delete(int id) {
        departmentMap.remove(id);
    }
}
