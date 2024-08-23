package com.EmployeeCRUD.repositories.impl.indb;

import com.EmployeeCRUD.models.Department;
import com.EmployeeCRUD.repositories.DepartmentRepository;
import com.EmployeeCRUD.util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBDepartmentRepository implements DepartmentRepository {
    private static final Logger logger = LoggerFactory.getLogger(DBDepartmentRepository.class);
    @Override
    public int add(Department department) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO DEPARTMENT (NAME) VALUES (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, department.getName());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Error in adding Department!", e);
        }
        return department.getId();
    }

    @Override
    public Department getById(int id) {
        Department department = null;
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM DEPARTMENT WHERE ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                department = new Department(
                        rs.getInt("ID"),
                        rs.getString("NAME"));
            }
        } catch (SQLException e) {
            logger.error("Error in getting Department!", e);
        }
        return department;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM DEPARTMENT")) {
            while (rs.next()) {
                departments.add(new Department(
                        rs.getInt("ID"),
                        rs.getString("NAME")));
            }
        } catch (SQLException e) {
            logger.error("Error in getting all Departments!", e);
        }
        return departments;
    }

    @Override
    public void update(Department department) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE DEPARTMENT SET NAME = ? WHERE ID = ?")) {
            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error in updating Department!", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "DELETE FROM DEPARTMENT WHERE ID = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error in deleting Department!", e);
        }
    }
}