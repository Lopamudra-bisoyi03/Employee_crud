package com.EmployeeCRUD.controller;

import com.EmployeeCRUD.models.Address;
import com.EmployeeCRUD.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddressController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
    private final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    public void addAddress(String location, int zip) {
        Address address = new Address(location, zip);
        addressService.addAddress(address);
        logger.debug("Address added successfully");
    }
    public Address getAddressById(int id) {
        return addressService.getAddressById(id);
    }
    public void updateAddress(int id, String location, int zip) {
        Address address = new Address(id, location, zip);
        addressService.updateAddress(address);
        logger.debug("Address updated successfully");
    }
    public void deleteAddress(int id) {
        addressService.deleteAddress(id);
        logger.debug("Address deleted successfully");
    }
}

