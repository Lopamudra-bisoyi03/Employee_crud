package com.EmployeeCRUD.servlet;

import com.EmployeeCRUD.models.Employee;
import com.EmployeeCRUD.models.User;
import com.EmployeeCRUD.service.EmployeeService;
import com.EmployeeCRUD.repositories.impl.indb.DBEmployeeRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/myServlet")
public class MyServlet extends HttpServlet {
    private final EmployeeService employeeService = new EmployeeService(new DBEmployeeRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // false: don't create a new session if one doesn't exist
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not logged in");
            return;
        }

        String username = (String) session.getAttribute("user");

        if (!"lopa".equals(username)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Forbidden");
            return;
        }

        String employeeIdParam = request.getParameter("employeeId");
        if (employeeIdParam == null || employeeIdParam.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Missing employeeId parameter");
            return;
        }

        int employeeId;
        try {
            employeeId = Integer.parseInt(employeeIdParam);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid employeeId parameter");
            return;
        }

        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee != null) {
            response.getWriter().write("Employee: " + employee.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Employee not found");
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not logged in");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Forbidden");
            return;
        }

        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            int departmentId = Integer.parseInt(request.getParameter("departmentId"));

            Employee employee = new Employee(0, name, email, addressId, departmentId);
            employeeService.addEmployee(employee);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("Employee added successfully: " + employee);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to add employee: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Reading parameters
            String idParam = request.getParameter("id");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String addressIdParam = request.getParameter("addressId");
            String departmentIdParam = request.getParameter("departmentId");

            if (idParam == null || name == null || email == null || addressIdParam == null || departmentIdParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing required parameters");
                return;
            }

            int id = Integer.parseInt(idParam);
            int addressId = Integer.parseInt(addressIdParam);
            int departmentId = Integer.parseInt(departmentIdParam);

            Employee employee = new Employee(id, name, email, addressId, departmentId);
            boolean updated = employeeService.updateEmployee(employee);

            if (updated) {
                response.getWriter().write("Employee updated successfully: " + employee);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Employee not found");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid number format");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to update employee: " + e.getMessage());
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not logged in");
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Forbidden");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean deleted = employeeService.deleteEmployee(id);

            if (deleted) {
                response.getWriter().write("Employee deleted successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Employee not found");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to delete employee: " + e.getMessage());
        }
    }
}
