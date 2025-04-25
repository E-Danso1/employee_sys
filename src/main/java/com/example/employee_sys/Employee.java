package com.example.employee_sys;


public class Employee<T> implements Comparable<Employee<T>> {


    // adding the fields
    private T employeeId;
    private String name;
    private String department;
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;


    public Employee(T id, String name, String department, double salary, double performance,
                    int yearsOfExperience, boolean isActive) {

//the constructor

        this.employeeId = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performance;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
    }

    // getters and setters

    public T getEmployeeid() {
        return employeeId;
    }

    public void setEmployeeId(T Employeeid) {
        this.employeeId = Employeeid;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        this.name = name;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.salary = salary;
    }

    public double getPerformanceRating() {
        return performanceRating;
    }
    public void setPerformanceRating(double performanceRating) {
        this.performanceRating = performanceRating;
    }
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean Active) {
        isActive = Active;
    }

    // Implementing Comparable to sort by yearsOfExperience in descending order
     @Override
    public int compareTo(Employee<T> other) {
        // For descending order, we compare other to this
        return Integer.compare(other.yearsOfExperience, this.yearsOfExperience);
    }

    // toString method for clean console output
    @Override
    public String toString() {
        return String.format(
                "ID: %-10s | Name: %-20s | Dept: %-10s | Salary: $%-10.2f | Rating: %-4.1f | Exp: %-2d yrs | Active: %-5s",
                employeeId, name, department, salary, performanceRating, yearsOfExperience, isActive ? "Yes" : "No"
        );
    }

}



