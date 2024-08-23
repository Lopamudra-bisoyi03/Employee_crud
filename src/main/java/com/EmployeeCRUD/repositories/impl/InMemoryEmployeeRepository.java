package com.EmployeeCRUD.repositories.impl;


import com.EmployeeCRUD.models.Employee;
import com.EmployeeCRUD.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryEmployeeRepository implements EmployeeRepository {
    private static final Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();
    private static int idCounter = 1;

    @Override
    public void add(Employee employee) {
        employee.setId(idCounter);
        employeeMap.put(idCounter, employee);
        idCounter++;
    }

    @Override
    public Employee getById(int id) {
        return employeeMap.get(id);
    }

    @Override
    public Employee getByName(String name) {
        return employeeMap.values().stream().filter(employee -> employee.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public List<Employee> getAll() {
        return List.copyOf(employeeMap.values());
    }

    @Override
    public void update(Employee employee) {
        employeeMap.put(employee.getId(), employee);
    }

    @Override
    public void delete(int id) {
        employeeMap.remove(id);
    }
    @Override
    public Employee getByEmail(String email) {
        return null;
    }
}
