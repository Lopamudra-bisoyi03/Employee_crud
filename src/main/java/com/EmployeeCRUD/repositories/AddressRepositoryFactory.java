package com.EmployeeCRUD.repositories;

import com.EmployeeCRUD.repositories.impl.*;
import com.EmployeeCRUD.repositories.impl.indb.DBAddressRepository;

public class AddressRepositoryFactory {
    public static AddressRepository getAddressRepository(boolean useFile, boolean useDatabase) {
        if (useDatabase) {
            return new DBAddressRepository();
        } else if (useFile) {
            return new FileAddressRepository();
        } else {
            return new InMemoryAddressRepository();
        }
    }
}
