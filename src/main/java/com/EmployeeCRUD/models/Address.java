package com.EmployeeCRUD.models;

public class Address {

    private int id;
    private String location;
    private int zip;

    // Constructors

    public Address(String location, int zip) {
        this.location = location;
        this.zip = zip;
    }

    public Address(int id, String location, int zip) {
        this.id = id;
        this.location = location;
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Address ID: " + id + " Location: " + location + " Zip: " + zip;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
}
