package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App {

    static List<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        EmployeeFactory employeeFactory = new EmployeeFactory();
        employeeList = employeeFactory.getAllEmployee();

        listDistinctProjectsNonAscendingOrder(employeeList);

        printFullNameOfAnyEmployeeFistNameStartsWithA(employeeList);

        listOfAllEmployeesJoinedIn2023(employeeList);

        printMinSalary(employeeList);

        printNamesOfAllEmployeesWithMinSalary(employeeList);

        listOfAllEmployeesWorkingOnMoreThanTwoProject(employeeList);

        countOfAllLaptopsAssignedToEmployees(employeeList);

        countOfAllProjectsWithGivenNameAsPM(employeeList, "Robert Downey Jr");

        listOfAllProjectsWithGivenNameAsPM(employeeList, "Robert Downey Jr");

        listOfAllPeopleWorkingWithGivenName(employeeList, "Robert Downey Jr");

        createMapOfData(employeeList);

        createMapOfCountJoinedParticularYear(employeeList);

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

    public static void printMinSalary(List<Employee> employeeList) {
        System.out.println("--- 6. Print minimum salary. ---");

        employeeList.stream()
            .sorted(Comparator.comparing(Employee::getSalary))
            .findFirst()
            .ifPresent(System.out::println);
    }

    public static void printNamesOfAllEmployeesWithMinSalary(List<Employee> employeeList) {
        System.out.println("--- 7. Print list of all employee with min salary. ---");

        employeeList.stream()
            .sorted(Comparator.comparing(Employee::getSalary))
            .findFirst()
            .ifPresent(employee -> employeeList.stream().filter(e -> employee.getSalary() == e.getSalary())
                .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName())));
    }

    public static void listOfAllEmployeesWorkingOnMoreThanTwoProject(List<Employee> employeeList) {
        System.out.println("--- 8. List of people working on more than 2 projects. ---");

        employeeList.stream()
            .filter(employee -> employee.getProjects().size() >= 2)
            .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName()));
    }

    public static void countOfAllLaptopsAssignedToEmployees(List<Employee> employeeList) {
        System.out.println("--- 9. Count of total laptops assigned to the employees. ---");

        int sumOfLaptos = employeeList.stream()
            .mapToInt(Employee::getTotalLaptopsAssigned)
            .sum();
        System.out.println("Sum of total laptops : " + sumOfLaptos);
    }

    public static void countOfAllProjectsWithGivenNameAsPM(List<Employee> employeeList, String name) {
        System.out.println("--- 10. Count of all projects with Robert Downey Jr as PM. ---");

        var count = employeeList.stream()
            .flatMap(employee -> employee.getProjects().stream())
            .filter(project -> name.equalsIgnoreCase(project.getProjectManager()))
            .distinct()
            .count();

        System.out.println("Count of all projects with Robert Downey Jr as PM : " + count);
    }

    public static void listOfAllProjectsWithGivenNameAsPM(List<Employee> employeeList, String name) {
        System.out.println("--- 11. List of all projects with Robert Downey Jr as PM. ---");

        employeeList.stream()
            .flatMap(employee -> employee.getProjects().stream())
            .filter(project -> name.equalsIgnoreCase(project.getProjectManager()))
            .distinct()
            .forEach(project -> System.out.println(project.getName()));
    }

    public static void listOfAllPeopleWorkingWithGivenName(List<Employee> employeeList, String name) {
        System.out.println("--- 12. List of all people working with Robert Downey Jr. ---");

        employeeList.stream()
            .filter(employee -> employee.getProjects().stream().anyMatch(p -> name.equalsIgnoreCase(p.getProjectManager())))
            .distinct()
            .forEach(e -> System.out.println(e.getFirstName() + " " + e.getLastName()));
    }

    public static void createMapOfData(List<Employee> employeeList) {
        System.out.println("--- 13. Create a map based on this data, they key should be the year of joining, and value should be list of all the employees who joined the particular year. ---");

        Map<String, List<Employee>> mapOfData = employeeList.stream()
            .collect(Collectors.groupingBy(s -> s.getId().substring(0, 4), Collectors.toList()));

        mapOfData.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEachOrdered(entry -> {
                System.out.println("Year : " + entry.getKey());
                entry.getValue().forEach(employee -> System.out.println(" " + employee.getFirstName() + " " + employee.getLastName()));
            });
    }

    private static void createMapOfCountJoinedParticularYear(List<Employee> employeeList) {
        System.out.println("--- 14. Create a map based on this data, the key should be year of joining and value should be the count ---");
        var createData = employeeList.stream()
            .collect(Collectors.groupingBy(employee -> employee.getId().substring(0, 4), Collectors.counting()));

        createData.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEachOrdered(entry -> System.out.println("Year: " + entry.getKey() + " count:" + entry.getValue()));
    }

}