package com.EmployeeCRUD.repositories;

import com.EmployeeCRUD.repositories.impl.*;
import com.EmployeeCRUD.repositories.impl.indb.DBEmployeeRepository;

public class EmployeeRepositoryFactory {
    public static EmployeeRepository getEmployeeRepository(boolean useFile, boolean useDatabase) {
        if (useDatabase) {
            return new DBEmployeeRepository();
        } else if (useFile) {
            return new FileEmployeeRepository();
        } else {
            return new InMemoryEmployeeRepository();
        }
    }
}
