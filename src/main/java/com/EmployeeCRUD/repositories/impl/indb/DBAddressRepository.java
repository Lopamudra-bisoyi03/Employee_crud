package com.EmployeeCRUD.repositories.impl.indb;

import com.EmployeeCRUD.models.Address;
import com.EmployeeCRUD.repositories.AddressRepository;
import com.EmployeeCRUD.util.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBAddressRepository implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(DBAddressRepository.class);
    @Override
    public int add(Address address) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO ADDRESS (LOCATION, ZIP) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, address.getLocation());
            stmt.setInt(2, address.getZip());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    address.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            logger.error("Error in adding Address!", e);
        }
        return address.getId();
    }

    @Override
    public Address getById(int id) {
        Address address = null;
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT * FROM ADDRESS WHERE ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                address = new Address(
                        rs.getInt("ID"),
                        rs.getString("LOCATION"),
                        rs.getInt("ZIP"));
            }
        } catch (SQLException e) {
            logger.error("Error in getting Address by Id!", e);
        }
        return address;
    }

    @Override
    public List<Address> getAll() {
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DatabaseConnector.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ADDRESS")) {
            while (rs.next()) {
                addresses.add(new Address(
                        rs.getInt("ID"),
                        rs.getString("LOCATION"),
                        rs.getInt("ZIP")));
            }
        } catch (SQLException e) {
            logger.error("Error in getting all Addresses!", e);
        }
        return addresses;
    }

    @Override
    public void update(Address address) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE ADDRESS SET LOCATION = ?, ZIP = ? WHERE ID = ?")) {
            stmt.setString(1, address.getLocation());
            stmt.setInt(2, address.getZip());
            stmt.setInt(3, address.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error in updating Address!", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement stmt = connection.prepareStatement(
                     "DELETE FROM ADDRESS WHERE ID = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error in deleting Address!", e);
        }
    }
}