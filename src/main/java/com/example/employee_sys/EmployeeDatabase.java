package com.example.employee_sys;

import java.util.*;
import java.util.stream.Collectors;


public class EmployeeDatabase<T, E> {
    private HashMap<T, Employee<T>> employeeMap = new HashMap<>();


    // CREATE: Add a new employee
    public void addEmployee(Employee<T> employee) {
//        if (employee == null || employeeMap.containsKey(employee.getEmployee())) {
//            return false;
//        }
        employeeMap.put(employee.getEmployeeid(), employee);
    }

    // READ: Get all employees
    public List<Employee<T>> getAllEmployees() {
        return new ArrayList<>(employeeMap.values());
    }

    // READ: Get a specific employee by ID
    public Employee<T> getEmployee(T id) {
        return employeeMap.get(id);
    }

    // UPDATE: Update employee details dynamically
    public boolean updateEmployeeDetails(T id, String fieldName, Object newValue) {
        Employee<T> employee = employeeMap.get(id);
        if (employee == null) {
            return false;
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
                case "yearsofexperience":
                    employee.setYearsOfExperience((Integer) newValue);
                    break;
                case "inactive":
                    employee.setActive((Boolean) newValue);
                    break;
                default:
                    return false; // Invalid field name
            }
            return true;
        } catch (ClassCastException e) {
            return false; // Invalid type for the field
        }
    }

    // DELETE: Remove an employee
    public boolean removeEmployee(T id) {
        return employeeMap.remove(id) != null;
    }

    // Additional method to get employees sorted by years of experience (descending)
    public List<Employee<T>> getAllEmployeesSortedByExperience() {
        List<Employee<T>> employees = new ArrayList<>(employeeMap.values());
        Collections.sort(employees);
        return employees;
    }

    // ---  Searching & Filtering with Streams ---

    public List<Employee<T>> getEmployeesByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Employee<T>> searchByName(String keyword) {
        return employeeMap.values().stream()
                .filter(e -> e.getName().toLowerCase().contains(keyword.toLowerCase()))
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
                .filter(e -> e.getPerformanceRating() >= ratingThreshold)
                .forEach(e -> e.setSalary(e.getSalary() + raiseAmount));
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
                .sorted(Comparator.comparing(Employee<T>::getName)) // Alphabetical sort for clean report
                .forEach(e -> System.out.printf("%-20s %-15s $%-9.2f %-10.1f %-10d %-5s\n",
                        e.getName(), e.getDepartment(), e.getSalary(), e.getPerformanceRating(),
                        e.getYearsOfExperience(), e.isActive() ? "Yes" : "No"));
    }






}
