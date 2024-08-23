package client;

import com.EmployeeCRUD.models.Address;
import com.EmployeeCRUD.models.Department;
import com.EmployeeCRUD.models.Employee;
import com.EmployeeCRUD.repositories.*;
import com.EmployeeCRUD.service.AddressService;
import com.EmployeeCRUD.service.DepartmentService;
import com.EmployeeCRUD.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ClientMain {
    public static final Logger logger = LoggerFactory.getLogger(ClientMain.class);
    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
    static int addressId = 0;
    static int departmentId = 0;

    public static void main(String[] args) {
        System.out.println("Choose repository type:");
        System.out.println("1. In-Memory");
        System.out.println("2. File-Based");
        System.out.println("3. Database");
        int choice = getValidIntInput();
        boolean useFile = (choice == 2);
        boolean useDatabase = (choice == 3);

        AddressRepository addressRepo = AddressRepositoryFactory.getAddressRepository(useFile, useDatabase);
        DepartmentRepository departmentRepo = DepartmentRepositoryFactory.getDepartmentRepository(useFile, useDatabase);
        EmployeeRepository employeeRepo = EmployeeRepositoryFactory.getEmployeeRepository(useFile, useDatabase);

        AddressService addressService = new AddressService(addressRepo);
        DepartmentService departmentService = new DepartmentService(departmentRepo);
        EmployeeService employeeService = new EmployeeService(employeeRepo, addressRepo, departmentRepo);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Get Employee by ID");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. List All Employees");
            System.out.println("6. Add Department");
            System.out.println("7. Get Department by ID");
            System.out.println("8. Update Department");
            System.out.println("9. Delete Department");
            System.out.println("10. List All Departments");
            System.out.println("11. Add Address");
            System.out.println("12. Get Address by ID");
            System.out.println("13. Update Address");
            System.out.println("14. Delete Address");
            System.out.println("15. List All Addresses");
            System.out.println("16. Exit");

            choice = getValidIntInput();

            switch (choice) {
                case 1:
                    addEmployee(employeeService);
                    break;
                case 2:
                    getEmployeeById(employeeService);
                    break;
                case 3:
                    updateEmployee(employeeService);
                    break;
                case 4:
                    deleteEmployee(employeeService);
                    break;
                case 5:
                    listAllEmployees(employeeService);
                    break;
                case 6:
                    addDepartment(departmentService);
                    break;
                case 7:
                    getDepartmentById(departmentService);
                    break;
                case 8:
                    updateDepartment(departmentService);
                    break;
                case 9:
                    deleteDepartment(departmentService);
                    break;
                case 10:
                    listAllDepartments(departmentService);
                    break;
                case 11:
                    addAddress(addressService);
                    break;
                case 12:
                    getAddressById(addressService);
                    break;
                case 13:
                    updateAddress(addressService);
                    break;
                case 14:
                    deleteAddress(addressService);
                    break;
                case 15:
                    listAllAddresses(addressService);
                    break;
                case 16:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addEmployee(EmployeeService employeeService) {
        try {
            String name = getValidStringInput("Enter employee name:");
            String email = getValidEmailInput();


            // Ask if user wants to add an address
            System.out.print("Do you want to add an address? (y/n): ");
            if ("y".equalsIgnoreCase(scanner.nextLine().trim())) {
                addAddress();
            }

            // Ask if user wants to add a department
            System.out.print("Do you want to add a department? (y/n): ");
            if ("y".equalsIgnoreCase(scanner.nextLine().trim())) {
                addDepartment();
            }
            
            Employee employee = new Employee(0, name, email, addressId, departmentId);
            employeeService.addEmployee(employee);
           // System.out.println("Employee added: " + employee);
        } catch (InputMismatchException e) {
            logger.error("Error while adding employee.", e);
        }
    }

    private static void addAddress() {
        // This method should include logic to add an address, similar to the original addAddress method
        String location = getValidStringInput("Enter address location:");
        int zip = Integer.parseInt(getValidStringInput("Enter address zip:"));
        Address address = new Address(location, zip);
        AddressService addressService = new AddressService(AddressRepositoryFactory.getAddressRepository(false, false));
        addressId = addressService.addAddress(address);
        System.out.println("Address added: " + address);
    }

    private static void addDepartment() {
        // This method should include logic to add a department, similar to the original addDepartment method
        String name = getValidStringInput("Enter department name:");
        Department department = new Department(0, name);
        DepartmentService departmentService = new DepartmentService(DepartmentRepositoryFactory.getDepartmentRepository(false, false));
        departmentId = departmentService.addDepartment(department);
        System.out.println("Department added: " + department);
    }

    private static void getEmployeeById(EmployeeService employeeService) {
        int id = getValidPositiveIntInput("Enter employee ID:");
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            System.out.println("Employee found: " + employee);
        } else {
            System.out.println("Employee not found.");
        }
    }

    private static void updateEmployee(EmployeeService employeeService) {
        int id = getValidPositiveIntInput("Enter employee ID to update:");
        String name = getValidStringInput("Enter new employee name:");
        String email = getValidEmailInput();
        int addressId = getValidPositiveIntInput("Enter new address ID:");
        int departmentId = getValidPositiveIntInput("Enter new department ID:");
        Employee employee = new Employee(id, name, email, addressId, departmentId);
        employeeService.updateEmployee(employee);
        System.out.println("Employee updated: " + employee);
    }

    private static void deleteEmployee(EmployeeService employeeService) {
        int id = getValidPositiveIntInput("Enter employee ID to delete:");
        employeeService.deleteEmployee(id);
        System.out.println("Employee deleted.");
    }

    private static void listAllEmployees(EmployeeService employeeService) {
        System.out.println("All employees:");
        for (Employee employee : employeeService.getAllEmployees()) {
            System.out.println(employee);
        }
    }

    private static void addAddress(AddressService addressService) {
        String location = getValidStringInput("Enter address location:");
        int zip = Integer.parseInt(getValidStringInput("Enter address zip:"));
        Address address = new Address(location, zip);
        addressService.addAddress(address);
        System.out.println("Address added: " + address);
    }

    private static void getAddressById(AddressService addressService) {
        int id = getValidPositiveIntInput("Enter address ID:");
        Address address = addressService.getAddressById(id);
        if (address != null) {
            System.out.println("Address found: " + address);
        } else {
            System.out.println("Address not found.");
        }
    }

    private static void updateAddress(AddressService addressService) {
        int id = getValidPositiveIntInput("Enter address ID to update:");
        String location = getValidStringInput("Enter new location:");
        int zip = Integer.parseInt(getValidStringInput("Enter new zip:"));
        Address address = new Address(location, zip);
        addressService.updateAddress(address);
        System.out.println("Address updated: " + address);
    }

    private static void deleteAddress(AddressService addressService) {
        int id = getValidPositiveIntInput("Enter address ID to delete:");
        addressService.deleteAddress(id);
        System.out.println("Address deleted.");
    }

    private static void listAllAddresses(AddressService addressService) {
        System.out.println("All addresses:");
        for (Address address : addressService.getAllAddresses()) {
            System.out.println(address);
        }
    }

    private static void addDepartment(DepartmentService departmentService) {
        String name = getValidStringInput("Enter department name:");
        Department department = new Department(0, name);
        departmentService.addDepartment(department);
        System.out.println("Department added: " + department);
    }

    private static void getDepartmentById(DepartmentService departmentService) {
        int id = getValidPositiveIntInput("Enter department ID:");
        Department department = departmentService.getDepartmentById(id);
        if (department != null) {
            System.out.println("Department found: " + department);
        } else {
            System.out.println("Department not found.");
        }
    }

    private static void updateDepartment(DepartmentService departmentService) {
        int id = getValidPositiveIntInput("Enter department ID to update:");
        String name = getValidStringInput("Enter new department name:");
        Department department = new Department(id, name);
        departmentService.updateDepartment(department);
        System.out.println("Department updated: " + department);
    }

    private static void deleteDepartment(DepartmentService departmentService) {
        int id = getValidPositiveIntInput("Enter department ID to delete:");
        departmentService.deleteDepartment(id);
        System.out.println("Department deleted.");
    }

    private static void listAllDepartments(DepartmentService departmentService) {
        System.out.println("All departments:");
        for (Department department : departmentService.getAllDepartments()) {
            System.out.println(department);
        }
    }

    private static String getValidStringInput(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }

    private static int getValidPositiveIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " ");
                int input = scanner.nextInt();
                if (input > 0) {
                    scanner.nextLine(); // Consume newline
                    return input;
                } else {
                    System.out.println("Input must be a positive integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static int getValidIntInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static String getValidEmailInput() {
        while (true) {
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            if (EMAIL_PATTERN.matcher(email).matches()) {
                return email;
            } else {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        }
    }
}