package com.EmployeeCRUD.models;

public class Department {
    private int departmentId;
    private String departmentName;

    //constructors
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department(int id, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    //getter-setter
    public int getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    //toString method
    @Override
    public String toString() {
        return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
    }

    public String getName() {
        return departmentName;
    }


    public int getId() {
        return departmentId;
    }

    public void setId(int id) {
        this.departmentId = id;
    }
}
