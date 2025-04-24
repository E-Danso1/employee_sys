package com.example.employee_sys.ExceptionHandling;

public class InvalidDepartmentException extends Exception{
    public InvalidDepartmentException (String message) {
        super(message);
    }
}
