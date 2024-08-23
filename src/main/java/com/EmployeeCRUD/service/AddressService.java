package com.EmployeeCRUD.service;

import com.EmployeeCRUD.models.Address;
import com.EmployeeCRUD.repositories.AddressRepository;

import java.util.List;

public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public int addAddress(Address address) {
        return addressRepository.add(address);
    }

    public Address getAddressById(int id) {
        return addressRepository.getById(id);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.getAll();
    }

    public void updateAddress(Address address) {
        addressRepository.update(address);
    }

    public void deleteAddress(int id) {
        addressRepository.delete(id);
    }
}