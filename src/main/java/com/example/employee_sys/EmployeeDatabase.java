package com.example.employee_sys;

import com.example.employee_sys.ExceptionHandling.EmployeeNotFoundException;
import com.example.employee_sys.ExceptionHandling.InvalidDepartmentException;
import com.example.employee_sys.ExceptionHandling.InvalidSalaryException;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDatabase<T, E> {
    private final HashMap<T, Employee<T>> employeeMap = new HashMap<>();

    List<String> validDepartments = Arrays.asList("HR", "Finance", "IT", "Marketing");

    // CREATE: Add a new employee
    public void addEmployee(Employee<T> employee) throws InvalidSalaryException, InvalidDepartmentException {
        if (employee == null) {
            System.out.println("Cannot add a null employee.");
            return;
        }

        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            System.out.println("Employee name cannot be null or empty.");
            return;
        }

        if (employee.getDepartment() == null || employee.getDepartment().trim().isEmpty()) {
            System.out.println("Employee department cannot be null or empty.");
            return;
        }

        if (employee.getSalary() < 0) {
            throw new InvalidSalaryException("Salary cannot be negative.");
        }

        // Convert valid departments to lowercase for comparison
        List<String> validDepartments = Arrays.asList("hr", "finance", "it", "marketing");

        // Compare department ignoring case
        boolean isValid = validDepartments.stream()
                .anyMatch(dept -> dept.equalsIgnoreCase(employee.getDepartment()));

        if (!isValid) {
            throw new InvalidDepartmentException("Invalid department: " + employee.getDepartment());
        }

        employeeMap.put(employee.getEmployeeid(), employee);
        System.out.println("Employee " + employee.getName() + " added successfully.");
    }

    public void AddEmployee(Employee<T> employee) throws InvalidSalaryException, InvalidDepartmentException {
        employeeMap.put(employee.getEmployeeid(), employee);
        System.out.println("Employee added successfully.");
    }

    // READ: Get all employees
    public List<Employee<T>> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    // READ: Get a specific employee by ID
    public Employee<T> getEmployee(T id) throws EmployeeNotFoundException {
        Employee<T> employee = employeeMap.get(id);

        if (employee == null) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found.");
        }
        return employee;
    }

    // NEW: Search for an employee by name and throw if not found
    public Employee<T> searchEmployeeByName(String name) throws EmployeeNotFoundException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search name cannot be null or empty.");
        }

        return employeeMap.values().stream()
                .filter(Objects::nonNull)
                .filter(e -> e.getName() != null && e.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with name '" + name + "' not found."));
    }

    // Existing searchByName (still useful for getting a list with a keyword)
    public List<Employee<T>> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be null or empty");
            return Collections.emptyList();
        }

        List<Employee<T>> result = employeeMap.values().stream()
                .filter(Objects::nonNull)
                .filter(e -> e.getName() != null && e.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No employees found with name containing: '" + keyword + "'");
        }

        return result;
    }

    // UPDATE: Update employee details dynamically
    public boolean updateEmployeeDetails(T id, String fieldName, Object newValue) throws EmployeeNotFoundException {
        Employee<T> employee = employeeMap.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Cannot update - employee with ID " + id + " does not exist.");
        }

        try {
            switch (fieldName.toLowerCase()) {
                case "name":
                    employee.setName((String) newValue);
                    break;
                case "department":
                    employee.setDepartment((String) newValue);
                    break;
                case "salary":
                    employee.setSalary((Double) newValue);
                    break;
                case "performance rating":
                    employee.setPerformanceRating((Double) newValue);
                    break;
                case "yearsofxperience":
                    employee.setYearsOfExperience((Integer) newValue);
                    break;
                case "inactive":
                    employee.setActive((Boolean) newValue);
                    break;
                default:
                    return false;
            }
            return true;
        } catch (ClassCastException e) {
            System.err.println("Update failed: " + e.getMessage());
            return false;
        }
    }

    // DELETE: Remove an employee
    public boolean removeEmployee(T id) {
        return employeeMap.remove(id) != null;
    }

    // Additional method to get employees sorted by years of experience (descending)
    public List<Employee<T>> getAllEmployeesSortedByExperience() {
        List<Employee<T>> employees = new ArrayList<>(employeeMap.values());

        if (employees.isEmpty()) {
            System.out.println("No employees available to sort.");
            return employees;
        }

        employees.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        return employees;
    }

    // ---  Searching & Filtering with Streams ---
    public List<Employee<T>> getEmployeesByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Employee<T>> getEmployeesSortedByName() {
        return employeeMap.values().stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Employee<T>::getName, Comparator.nullsLast(String::compareToIgnoreCase)))
                .collect(Collectors.toList());
    }

    public List<Employee<T>> getEmployeesByPerformance(double minRating) {
        return employeeMap.values().stream()
                .filter(e -> e.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }

    public List<Employee<T>> getEmployeesBySalaryRange(double minSalary, double maxSalary) {
        return employeeMap.values().stream()
                .filter(e -> e.getSalary() >= minSalary && e.getSalary() <= maxSalary)
                .collect(Collectors.toList());
    }

    // --- Iterator to traverse all employees ---
    public Iterator<Employee<T>> getEmployeeIterator() {
        return employeeMap.values().iterator();
    }

    // Give a raise to employees with rating >= 4.5
    public void giveRaiseToHighPerformers(double ratingThreshold, double raiseAmount) {
        employeeMap.values().stream()
                .filter(e -> e.getPerformanceRating() > ratingThreshold)
                .forEach(e -> {
                    e.setSalary(e.getSalary() + raiseAmount);  // Fixed typo: should add not subtract
                    System.out.println("Giving raise to: " + e.getName());
                });
    }

    // Get Top 5 highest-paid employees
    public List<Employee<T>> getTop5HighestPaidEmployees() {
        return employeeMap.values().stream()
                .sorted(Comparator.comparingDouble(Employee<T>::getSalary).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    // Get average salary in a department
    public double getAverageSalaryByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    // For-each loop display
    public void displayEmployeesUsingForEach() {
        System.out.println("\n--- Employee List (For-Each Loop) ---");
        System.out.printf("%-20s %-15s %-10s %-10s %-10s %-5s\n", "Name", "Department", "Salary", "Rating", "Experience", "Active");
        System.out.println("-------------------------------------------------------------------------------");

        for (Employee<T> e : employeeMap.values()) {
            System.out.printf("%-20s %-15s $%-9.2f %-10.1f %-10d %-5s\n",
                    e.getName(), e.getDepartment(), e.getSalary(), e.getPerformanceRating(),
                    e.getYearsOfExperience(), e.isActive() ? "Yes" : "No");
        }
    }

    // Stream-based formatted report
    public void displayEmployeesUsingStream() {
        System.out.println("\n--- Employee Report (Stream API) ---");
        System.out.printf("%-20s %-15s %-10s %-10s %-10s %-5s\n", "Name", "Department", "Salary", "Rating", "Experience", "Active");
        System.out.println("-------------------------------------------------------------------------------");

        employeeMap.values().stream()
                .sorted(Comparator.comparing(Employee<T>::getName))
                .forEach(e -> System.out.printf("%-20s %-15s $%-9.2f %-10.1f %-10d %-5s\n",
                        e.getName(), e.getDepartment(), e.getSalary(), e.getPerformanceRating(),
                        e.getYearsOfExperience(), e.isActive() ? "Yes" : "No"));
    }
}
