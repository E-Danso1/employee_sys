package com.example.employee_sys.ExceptionHandling;

public class InvalidSalaryException extends Exception{
    public InvalidSalaryException(String message) {
        super(message);

    }
}
