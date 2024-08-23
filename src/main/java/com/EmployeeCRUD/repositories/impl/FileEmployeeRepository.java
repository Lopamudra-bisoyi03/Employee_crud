package com.EmployeeCRUD.repositories.impl;

import com.EmployeeCRUD.models.Employee;
import com.EmployeeCRUD.repositories.EmployeeRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileEmployeeRepository implements EmployeeRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileEmployeeRepository.class);
    private static final String FILE_NAME = "employees.txt";
    private static int maxEmpId = 0;

    @Override
    public void add(Employee employee) {
        if (maxEmpId == 0) {
            maxEmpId = generateEmpId() + 1;
        } else {
            maxEmpId++;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(maxEmpId + "," + employee.getName() + "," + employee.getEmail() + "," + employee.getAddressId() + "," + employee.getDepartmentId());
            writer.newLine();
        } catch (IOException e) {
            logger.error("Error in adding employee to the file");
        }
    }

    private int generateEmpId() {
        int maxEmpId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentEmpId = Integer.parseInt(parts[0]);
                maxEmpId = Math.max(maxEmpId, currentEmpId);

            }
            return maxEmpId;

        } catch (IOException e) {
            logger.error("Error in reading employee by id from the file");
        }
        return maxEmpId;
    }

    @Override
    public Employee getById(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == id) {
                    return new Employee(id, parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                }
            }
        } catch (IOException e) {
            logger.error("Error in reading employee by id from the file");
        }
        return null;
    }

    @Override
    public Employee getByName(String name) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equalsIgnoreCase(name)) {
                    int id = Integer.parseInt(parts[0]);
                    return new Employee(id, parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                }
            }
        } catch (IOException e) {
            logger.error("Error in reading employee by name from the file");
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];
                int addressId = Integer.parseInt(parts[3]);
                int departmentId = Integer.parseInt(parts[4]);
                employees.add(new Employee(id, name, email, addressId, departmentId));
            }
        } catch (IOException e) {
            logger.error("Error in reading all employees from the file");
        }
        return employees;
    }

    @Override
    public void update(Employee employee) {
        File tempFile = new File("temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == employee.getId()) {
                    writer.write(employee.getId() + "," + employee.getName() + "," + employee.getAddressId() + "," + employee.getDepartmentId());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            logger.error("Error in updating employee from the file");
        }
        tempFile.renameTo(new File(FILE_NAME));
    }

    @Override
    public void delete(int id) {
        File tempFile = new File("temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) != id) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            logger.error("Error in deleting employee from the file");
        }
        tempFile.renameTo(new File(FILE_NAME));
    }

    @Override
    public Employee getByEmail(String email) {
        return null;
    }
}