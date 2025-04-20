package com.example.employee_sys;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import java.util.Comparator;
import java.util.stream.Collectors;

public class HelloController {

    // FXML injected fields
    @FXML private TextField EmployeeDepartment;
    @FXML private TextField EmployeeName;
    @FXML private TextField EmployeeSalary;
    @FXML private TextField EmployeeYOE;
    @FXML private Button Employee_AddBtn;
    @FXML private Button Employee_ApplyBtn;
    @FXML private Button Employee_RemoveBtn;
    @FXML private ComboBox<String> Employee_filter_Department;
    @FXML private Button Employee_search;
    @FXML private ComboBox<String> Employee_sort_Experience;
    @FXML private TextField employeeId;
    @FXML private TreeTableColumn<Employee<?>, Integer> employeeTableYOExperince;
    @FXML private TreeTableColumn<Employee<?>, ?> employeeTable_Id;
    @FXML private TreeTableColumn<Employee<?>, Boolean> employee_Table_Active;
    @FXML private TreeTableColumn<Employee<?>, String> employee_Table_Department;
    @FXML private TreeTableColumn<Employee<?>, String> employee_Table_Name;
    @FXML private TreeTableColumn<Employee<?>, Double> employee_Table_Salary;
    @FXML private TreeTableColumn<Employee<?>, Double> employee_Table_performace;
    @FXML private TextField myTextfield;
    @FXML private TreeTableView<Employee<?>> employeeTreeTable;

    // Data storage
    private ObservableList<Employee<?>> employeeList = FXCollections.observableArrayList();
    private TreeItem<Employee<?>> rootItem = new TreeItem<>();

    // Initialize method called after FXML loading
    @FXML
    public void initialize() {
        // Set up TreeTableView columns
        employeeTable_Id.setCellValueFactory(new TreeItemPropertyValueFactory<>("employeeId"));
        employee_Table_Name.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        employee_Table_Department.setCellValueFactory(new TreeItemPropertyValueFactory<>("department"));
        employee_Table_Salary.setCellValueFactory(new TreeItemPropertyValueFactory<>("salary"));
        employee_Table_performace.setCellValueFactory(new TreeItemPropertyValueFactory<>("performanceRating"));
        employeeTableYOExperince.setCellValueFactory(new TreeItemPropertyValueFactory<>("yearsOfExperience"));
        employee_Table_Active.setCellValueFactory(new TreeItemPropertyValueFactory<>("active"));

        // Set up ComboBox options
        Employee_sort_Experience.getItems().addAll("Highest Experience", "Lowest Experience");
        Employee_filter_Department.getItems().add("All Departments");

        // Set root for TreeTableView
        employeeTreeTable.setRoot(rootItem);
        employeeTreeTable.setShowRoot(false);

        // Set up button actions
        Employee_AddBtn.setOnAction(event -> addEmployee());
        Employee_RemoveBtn.setOnAction(event -> removeEmployee());
        Employee_search.setOnAction(event -> searchEmployee());
        Employee_ApplyBtn.setOnAction(event -> applyFilters());
    }

    // Add new employee
    private void addEmployee() {
        try {
            int id = Integer.parseInt(employeeId.getText());
            String name = EmployeeName.getText();
            String department = EmployeeDepartment.getText();
            double salary = Double.parseDouble(EmployeeSalary.getText());
            int years = Integer.parseInt(EmployeeYOE.getText());

            // Default performance rating (you can modify this)
            double performance = 3.5;

            Employee<Integer> newEmployee = new Employee<>(
                    id, name, department, salary, performance, years, true);

            employeeList.add(newEmployee);
            updateTreeTable();

            // Update department filter options
            updateDepartmentFilter();

            // Clear input fields
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter valid numbers for ID, Salary, and Years of Experience.");
        }
    }

    // Remove selected employee
    private void removeEmployee() {
        TreeItem<Employee<?>> selectedItem = employeeTreeTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            employeeList.remove(selectedItem.getValue());
            updateTreeTable();
            updateDepartmentFilter();
        } else {
            showAlert("No Selection", "Please select an employee to remove.");
        }
    }

    // Search employee by name
    private void searchEmployee() {
        String searchText = myTextfield.getText().toLowerCase();
        if (searchText.isEmpty()) {
            updateTreeTable(); // Show all if search is empty
            return;
        }

        rootItem.getChildren().clear();
        employeeList.stream()
                .filter(emp -> emp.getName().toLowerCase().contains(searchText))
                .forEach(emp -> rootItem.getChildren().add(new TreeItem<>(emp)));
    }

    // Apply filters and sorting
    private void applyFilters() {
        String departmentFilter = Employee_filter_Department.getValue();
        String experienceSort = Employee_sort_Experience.getValue();

        ObservableList<Employee<?>> filteredList = employeeList;

        // Apply department filter
        if (departmentFilter != null && !departmentFilter.equals("All Departments")) {
            filteredList = employeeList.stream()
                    .filter(emp -> emp.getDepartment().equals(departmentFilter))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        // Apply experience sorting
        if (experienceSort != null) {
            Comparator<Employee<?>> comparator = experienceSort.equals("Highest Experience")
                    ? Comparator.comparingInt((Employee<?> emp) -> emp.getYearsOfExperience()).reversed()
                    : Comparator.comparingInt((Employee<?> emp) -> emp.getYearsOfExperience());

            FXCollections.sort(filteredList, comparator);
        }

        // Update TreeTableView
        rootItem.getChildren().clear();
        filteredList.forEach(emp -> rootItem.getChildren().add(new TreeItem<>(emp)));
    }

    // Update TreeTableView with all employees
    private void updateTreeTable() {
        rootItem.getChildren().clear();
        employeeList.forEach(emp -> rootItem.getChildren().add(new TreeItem<>(emp)));
    }

    // Update department filter options
    private void updateDepartmentFilter() {
        ObservableList<String> departments = employeeList.stream()
                .map(Employee::getDepartment)
                .distinct()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        departments.add(0, "All Departments");
        Employee_filter_Department.setItems(departments);
        Employee_filter_Department.getSelectionModel().selectFirst();
    }

    // Clear input fields
    private void clearFields() {
        employeeId.clear();
        EmployeeName.clear();
        EmployeeDepartment.clear();
        EmployeeSalary.clear();
        EmployeeYOE.clear();
    }

    // Show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}