package com.example.employee_sys;

import java.util.UUID;

public class EmployeeID {
    private final String id;

    public EmployeeID() {
        this.id = UUID.randomUUID().toString(); // Generates a random UUID
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        return  id;
    }
}
