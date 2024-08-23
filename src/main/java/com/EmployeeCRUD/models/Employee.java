package com.EmployeeCRUD.models;

public class Employee {


    private int id;
    private String name;
    private final String email;
    private int addressId;
    private int departmentId;

    // Constructors
    public Employee(int id, String name, String email, int addressId, int departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.addressId = addressId;
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", addressId=" + addressId+ ", departmentId=" + departmentId + "]";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddress(int addressId) {
        this.addressId = addressId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartment(int departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isAdmin() {
        return this.isAdmin();
    }
}