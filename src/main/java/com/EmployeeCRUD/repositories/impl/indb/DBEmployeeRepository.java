package com.EmployeeCRUD.repositories.impl.indb;

import com.EmployeeCRUD.models.Employee;
import com.EmployeeCRUD.repositories.EmployeeRepository;
import com.EmployeeCRUD.util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBEmployeeRepository implements EmployeeRepository {
    private static final Logger logger = LoggerFactory.getLogger(DBEmployeeRepository.class);
    @Override
    public void add(Employee employee) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO EMPLOYEE (NAME, EMAIL, ADDRESS_ID, DEPARTMENT_ID) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setInt(3, employee.getAddressId());
            stmt.setInt(4, employee.getDepartmentId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Error while adding employee", e);
        }
    }

    @Override
    public Employee getById(int id) {
        Employee employee = null;
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM EMPLOYEE WHERE ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getInt("DEPARTMENT_ID"));
            }
        } catch (SQLException e) {
            logger.error("Error while getting employee", e);
        }
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEE")) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getInt("DEPARTMENT_ID")));
            }
        } catch (SQLException e) {
            logger.error("Error while getting all employees", e);
        }
        return employees;
    }

    @Override
    public void update(Employee employee) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE EMPLOYEE SET NAME = ?, EMAIL = ?, ADDRESS_ID = ?, DEPARTMENT_ID = ? WHERE ID = ?")) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setInt(3, employee.getAddressId());
            stmt.setInt(4, employee.getDepartmentId());
            stmt.setInt(5, employee.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while updating employee", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "DELETE FROM EMPLOYEE WHERE ID = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error while deleting employee", e);
        }
    }

    @Override
    public Employee getByEmail(String email) {
        return null;
    }

    @Override
    public Employee getByName(String name) {
        Employee employee = null;
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM EMPLOYEE WHERE NAME = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("ID"),
                        rs.getString("NAME"),
                        rs.getString("EMAIL"),
                        rs.getInt("ADDRESS_ID"),
                        rs.getInt("DEPARTMENT_ID"));
            }
        } catch (SQLException e) {
            logger.error("Error while getting Employee by name", e);
        }
        return employee;
    }
}