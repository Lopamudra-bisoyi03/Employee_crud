package com.EmployeeCRUD.service;

import com.EmployeeCRUD.models.Address;
import com.EmployeeCRUD.models.Department;
import com.EmployeeCRUD.models.Employee;
import com.EmployeeCRUD.repositories.AddressRepository;
import com.EmployeeCRUD.repositories.DepartmentRepository;
import com.EmployeeCRUD.repositories.EmployeeRepository;
import com.EmployeeCRUD.repositories.impl.InMemoryEmployeeRepository;

import java.util.List;
import java.util.Scanner;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AddressRepository addressRepository;
    private final DepartmentRepository departmentRepository;
    private final Scanner scanner = new Scanner(System.in);

    public EmployeeService(InMemoryEmployeeRepository employeeRepository, AddressRepository addressRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
        this.departmentRepository = departmentRepository;
    }

    public EmployeeService(InMemoryEmployeeRepository inMemoryEmployeeRepository, EmployeeRepository employeeRepository, AddressRepository addressRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
        this.departmentRepository = departmentRepository;
    }

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        addressRepository = null;
        departmentRepository = null;
    }

    public EmployeeService(EmployeeRepository employeeRepository, AddressRepository addressRepository, DepartmentRepository departmentRepository) {

        this.employeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
        this.departmentRepository = departmentRepository;
    }



    public void addEmployeeWithAddressAndDepartment() {
        System.out.println("Enter Employee Name:");
        String name = scanner.nextLine();

        System.out.println("Enter Employee Email:");
        String email = scanner.nextLine();

        int addressId = 0;
        System.out.println("Do you want to add an address? (y/n)");
        String addAddressResponse = scanner.nextLine();
        if ("y".equalsIgnoreCase(addAddressResponse)) {
            System.out.println("Enter Address Location:");
            String location = scanner.nextLine();

            System.out.println("Enter Address Zip:");
            int zip = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Address address = new Address(0, location, zip);
            addressRepository.add(address);
            addressId = addressRepository.getAll().get(addressRepository.getAll().size() - 1).getId();
        }

        int departmentId = 0;
        System.out.println("Do you want to add a department? (y/n)");
        String addDepartmentResponse = scanner.nextLine();
        if ("y".equalsIgnoreCase(addDepartmentResponse)) {
            System.out.println("Enter Department Name:");
            String departmentName = scanner.nextLine();

            Department department = new Department(0, departmentName);
            departmentRepository.add(department);
            departmentId = departmentRepository.getAll().get(departmentRepository.getAll().size() - 1).getDepartmentId();
        }

        Employee employee = new Employee( 0, name, email, addressId, departmentId);
        employeeRepository.add(employee);
        System.out.println("Employee added successfully.");
    }

    public Employee getEmployeeById(int id) {
        return employeeRepository.getById(id);
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.getByEmail(email);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    public void updateEmployee(int id, String name, String email, int addressId, int departmentId) {
        Employee employee = new Employee( id, name, email, addressId, departmentId);
        employeeRepository.update(employee);
        System.out.println("Employee updated successfully.");
    }

    public boolean deleteEmployee(int id) {
        employeeRepository.delete(id);
        System.out.println("Employee deleted successfully.");
        return false;
    }

    public void addEmployee(Employee employee) {
        employeeRepository.add(employee);
    }

    public boolean updateEmployee(Employee employee) {
        employeeRepository.update(employee);
        return false;
    }

    public Employee getByEmail(String email) {
        return employeeRepository.getByEmail(email);
    }
}