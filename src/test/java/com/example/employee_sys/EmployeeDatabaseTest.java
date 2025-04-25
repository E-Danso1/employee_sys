package com.example.employee_sys;

import com.example.employee_sys.*;
import com.example.employee_sys.ExceptionHandling.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDatabaseTest {

    private EmployeeDatabase<Integer, Employee<Integer>> db;

    @BeforeEach
    public void setup() {
        db = new EmployeeDatabase<>();
    }

    @Test
    public void testAddEmployee() throws Exception {
        Employee<Integer> emp = new Employee<>(1, "Alice", "IT", 50000, 4.5, 3, true);
        db.AddEmployee(emp);
        assertEquals(emp, db.getEmployee(1));
    }

    @Test
    public void testSearchByDepartment() throws InvalidSalaryException, InvalidDepartmentException {
        db.addEmployee(new Employee<>(2, "Bob", "HR", 40000, 4.0, 2, true));
        assertFalse(db.getEmployeesByDepartment("Finance").contains("Bob"));
    }

    @Test
    public void testRemoveEmployee() throws InvalidSalaryException, InvalidDepartmentException {
        db.addEmployee(new Employee<>(3, "Eve", "Marketing", 45000, 3.9, 5, true));
        db.removeEmployee(3);
        assertThrows(EmployeeNotFoundException.class, () -> db.getEmployee(3));
    }
}
