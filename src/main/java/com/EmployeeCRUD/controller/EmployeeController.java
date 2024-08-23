package com.EmployeeCRUD.controller;

import com.EmployeeCRUD.models.Address;
import com.EmployeeCRUD.models.Department;
import com.EmployeeCRUD.models.Employee;
import com.EmployeeCRUD.service.AddressService;
import com.EmployeeCRUD.service.DepartmentService;
import com.EmployeeCRUD.service.EmployeeService;
import jakarta.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class EmployeeController  {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;
    private final AddressService addressService;
    private final DepartmentService departmentService;

    //constructor
    public EmployeeController(EmployeeService employeeService, AddressService addressService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.addressService = addressService;
        this.departmentService = departmentService;
    }

    //method to add employee data
    public void addEmployee() {
        Scanner scanner = new Scanner(System.in);

        //collect employee data
        System.out.println("Enter employee name: ");
        String name = scanner.nextLine();

        System.out.println("Enter employee email: ");
        String email = scanner.nextLine();

        //address
        Address address = null;
        System.out.println("Do you want to add an Address? (y/n): ");
        String addAddress = scanner.nextLine().trim().toLowerCase();
        if (addAddress.equals("y")) {
            System.out.println("Enter address location: ");
            String location = scanner.nextLine();

            System.out.println("Enter address zip: ");
            int zip = scanner.nextInt();
            scanner.nextLine();

           address = new Address( location, zip);
           addressService.addAddress(address);
        }

        //department
        Department department = null;
        System.out.println("Do you want to add an Department? (y/n): ");
        String addDepartment = scanner.nextLine().trim().toLowerCase();
        if (addDepartment.equals("y")) {
            System.out.println("Enter department name: ");
            String departmentName = scanner.nextLine();

            department = new Department( departmentName);
            departmentService.addDepartment(department);
        }

        //determine addressId and departmentId
        int addressId = (address != null) ? address.getId() : 0;
        int departmentId = (department != null) ? department.getId() : 0;

        //add employee
        Employee employee = new Employee( 0, name, email, addressId, departmentId);
        employeeService.addEmployee(employee);
        logger.debug("Employee added: " + employee);
    }

    public Employee getEmployee(int id) {
        return employeeService.getEmployeeById(id);
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeService.getByEmail(email);
    }

    public void updateEmployee(int id, String name, String email, int addressId, int departmentId) {
        Employee employee = new Employee(id, name, email, addressId, departmentId);
        employeeService.updateEmployee(employee);
        logger.debug("Employee updated successfully");
    }

    public void deleteEmployee(int id) {
        employeeService.deleteEmployee(id);
        logger.debug("Employee deleted successfully");
    }

}
