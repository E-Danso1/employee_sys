package com.example.employee_sys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EmployeeApp.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

             // Create database instance
                EmployeeDatabase<EmployeeID, Employee<EmployeeID>> database = new EmployeeDatabase<>();
                EmployeeID id = new EmployeeID();
        EmployeeID id2 = new EmployeeID();
        EmployeeID id3 = new EmployeeID();
        EmployeeID id4 = new EmployeeID();
        EmployeeID id5 = new EmployeeID();
        EmployeeID id6 = new EmployeeID();
        EmployeeID id7 = new EmployeeID();



        // Add employees
                database.addEmployee(new Employee<>(id, "Ernest Danso", "IT", 75000, 4.5, 5, true));
                database.addEmployee(new Employee<>(id2, "James Asare", "IT", 85000, 4.4, 4, true));
               database.addEmployee(new Employee<>(id3, "McCall Watkins", "HR", 78000, 4.2, 4, true));
               database.addEmployee(new Employee<>(id4, "Michael Opoku", "FINANCE", 75000, 4.0, 5, true));
               database.addEmployee(new Employee<>(id5, "Eugene Omari", "FINANCE", 75000, 4.5, 4, true));
               database.addEmployee(new Employee<>(id6, "Johnson Agyei", "HR", 88000, 4.0, 5, false));
                database.addEmployee(new Employee<>(id7, "Samuel Nkoom", "IT", 77000, 4.3, 3, true));

                // Get updated list
                System.out.println(database.getAllEmployees());


 launch();

        }

}