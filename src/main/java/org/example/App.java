package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App {

    static List<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        EmployeeFactory employeeFactory = new EmployeeFactory();
        employeeList = employeeFactory.getAllEmployee();

        listDistinctProjectsNonAscendingOrder(employeeList);

        printFullNameOfAnyEmployeeFistNameStartsWithA(employeeList);

        listOfAllEmployeesJoinedIn2023(employeeList);

        printNamesOfEmployeesWithNthHighestSalary(employeeList, 3);

    }

    public static void listDistinctProjectsNonAscendingOrder(List<Employee> employeeList) {
        System.out.println("--- 1. List all distinct project in non-ascending order. ---");

        employeeList.stream()
            .flatMap(employee -> employee.getProjects().stream())
            .sorted(Comparator.comparing(Project::getName, Comparator.reverseOrder()))
            .distinct()
            .forEachOrdered(project -> System.out.println(project.getName()));
    }

    public static void printFullNameOfAnyEmployeeFistNameStartsWithA(List<Employee> employeeList) {
        System.out.println("--- 2. Print full name of any employee whose firstName starts with ‘A’. ---");

        employeeList.stream()
            .filter(employee -> employee.getFirstName().startsWith("A"))
            .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName()));
    }

    public static void listOfAllEmployeesJoinedIn2023(List<Employee> employeeList) {
        System.out.println("--- 3. List of all employee who joined in year 2023. ---");

        employeeList.stream()
            .filter(employee -> employee.getId().startsWith("2023"))
            .forEach(employee -> System.out.println(employee.toString()));
    }

    public static void sortEmployeesBasedOnFirstNameForSameSortForSalary(List<Employee> employeeList) {
        System.out.println("--- 4. Sort employees based on firstName, for same firstName sort by salary. ---");

        employeeList.stream()
            .sorted(Comparator.comparing(Employee::getFirstName).thenComparing(Employee::getSalary))
            .forEachOrdered(employee -> System.out.println(employee.toString()));
    }

    public static void printNamesOfEmployeesWithNthHighestSalary(List<Employee> employeeList, int n) {
        System.out.println("--- 5. Print names of all employee with 3rd highest salary. (generalise it for nth highest salary). ---");

        employeeList.stream()
            .sorted(Comparator.comparing(Employee::getSalary, Comparator.reverseOrder()))
            .limit(n)
            .skip(n - 1)
            .findFirst()
            .ifPresent(nThHighestEmployee -> employeeList.stream()
                .filter(e -> e.getSalary() == nThHighestEmployee.getSalary())
                .forEach(k -> System.out.println(k.toString()))
            );
    }

}