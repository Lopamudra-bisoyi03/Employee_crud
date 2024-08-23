package com.EmployeeCRUD.repositories;

import com.EmployeeCRUD.models.Address;

import java.util.List;

public interface AddressRepository {
    int  add(Address address);
    Address getById(int id);
    List<Address> getAll();
    void update(Address address);
    void delete(int id);
}
