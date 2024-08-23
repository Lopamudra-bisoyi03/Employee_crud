package com.EmployeeCRUD.repositories.impl;

import com.EmployeeCRUD.models.Department;
import com.EmployeeCRUD.repositories.DepartmentRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDepartmentRepository implements DepartmentRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileDepartmentRepository.class);
    private static final String FILE_NAME = "departments.txt";

    @Override
    public int add(Department department) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(department.getDepartmentId() + "," + department.getDepartmentName());
            writer.newLine();
        } catch (IOException e) {
            logger.error("Error adding department to file", e);
        }
        return department.getDepartmentId();
    }

    @Override
    public Department getById(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == id) {
                    return new Department(id, parts[1]);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading department from file", e);
        }
        return null;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                departments.add(new Department(id, name));
            }
        } catch (IOException e) {
            logger.error("Error getting all departments from file", e);
        }
        return departments;
    }

    @Override
    public void update(Department department) {
        File tempFile = new File("temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == department.getDepartmentId()) {
                    writer.write(department.getDepartmentId() + "," + department.getDepartmentName());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            logger.error("Error updating department to file", e);
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
            logger.error("Error deleting department from file", e);
        }
        tempFile.renameTo(new File(FILE_NAME));
    }
}