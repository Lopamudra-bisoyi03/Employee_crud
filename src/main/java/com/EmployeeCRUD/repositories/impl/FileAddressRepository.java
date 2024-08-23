package com.EmployeeCRUD.repositories.impl;

import com.EmployeeCRUD.models.Address;
import com.EmployeeCRUD.repositories.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileAddressRepository implements AddressRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileAddressRepository.class);

    private static final String FILE_NAME = "addresses.txt";
    private static final Logger log = LoggerFactory.getLogger(FileAddressRepository.class);


    @Override
    public int add(Address address) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(address.getId() + "," + address.getLocation() + "," + address.getZip());
            writer.newLine();
        } catch (IOException e) {
            logger.error("Error adding address to file", e);
        }
        return address.getId();
    }

    @Override
    public Address getById(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == id) {
                    return new Address(id, parts[1], Integer.parseInt(parts[2]));
                }
            }
        } catch (IOException e) {
            logger.error("Error reading address by id from file", e);
        }
        return null;
    }

    @Override
    public List<Address> getAll() {
        List<Address> addresses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String location = parts[1];
                int zip = Integer.parseInt(parts[2]);
                addresses.add(new Address(id, location, zip));
            }
        } catch (IOException e) {
            logger.error("Error reading all addresses from file", e);
        }
        return addresses;
    }

    @Override
    public void update(Address address) {
        File tempFile = new File("temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == address.getId()) {
                    writer.write(address.getId() + "," + address.getLocation() + "," + address.getZip());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            logger.error("Error updating address to file", e);
        }
        tempFile.renameTo(new File(FILE_NAME));
    }

    @Override
    public void delete(int id) {
        File tempFile = getFile();
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
            logger.error("Error deleting address by id from file", e);
        }
    }

    private static File getFile() {
        return new File("temp.txt");
    }
}