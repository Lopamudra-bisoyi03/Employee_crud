package com.EmployeeCRUD.repositories.impl;

import com.EmployeeCRUD.models.Address;
import com.EmployeeCRUD.repositories.AddressRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryAddressRepository implements AddressRepository {
    private final Map<Integer, Address> addressMap = new HashMap<>();
    private static int idCounter = 1;

    @Override
    public int add(Address address) {
        address.setId(idCounter++);
        addressMap.put(address.getId(), address);
        return address.getId();
    }

    @Override
    public Address getById(int id) {
        return addressMap.get(id);
    }

    @Override
    public List<Address> getAll() {
        return new ArrayList<>(addressMap.values());
    }

    @Override
    public void update(Address address) {
        if (addressMap.containsKey(address.getId())) {
            addressMap.put(address.getId(), address);
        }
    }

    @Override
    public void delete(int id) {
        addressMap.remove(id);
    }
}
