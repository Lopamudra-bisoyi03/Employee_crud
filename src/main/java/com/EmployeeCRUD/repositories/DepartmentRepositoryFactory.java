package com.EmployeeCRUD.repositories;

import com.EmployeeCRUD.repositories.impl.indb.DBDepartmentRepository;
import com.EmployeeCRUD.repositories.impl.FileDepartmentRepository;
import com.EmployeeCRUD.repositories.impl.InMemoryDepartmentRepository;

public class DepartmentRepositoryFactory {
    public static DepartmentRepository getDepartmentRepository(boolean useFile, boolean useDatabase) {
        if (useDatabase) {
            return new DBDepartmentRepository();
        } else if (useFile) {
            return new FileDepartmentRepository();
        } else {
            return new InMemoryDepartmentRepository();
        }
    }
}