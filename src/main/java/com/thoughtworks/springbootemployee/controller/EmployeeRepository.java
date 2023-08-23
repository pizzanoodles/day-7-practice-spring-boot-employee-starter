package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();

    static {
        employees.add(new Employee(1L, 2L, "Alice", 30, "Female", 5000));
        employees.add(new Employee(2L, 2L, "Bob", 31, "Male", 6000));
        employees.add(new Employee(3L, 1L, "Carl", 32, "Male", 7000));
        employees.add(new Employee(4L, 1L, "David", 33, "Male", 8000));
        employees.add(new Employee(5L, 5L, "Elen", 34, "Female", 9000));
    }

    // TODO: change id to employeeId

    public List<Employee> listAllEmployees() {
        return employees;
    }

    public Employee findEmployeeById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        Employee employeeToBeAdded = new Employee(generateId(), employee.getCompanyId(), employee.getName(), employee.getAge(), employee.getGender(), employee.getSalary());
        employees.add(employeeToBeAdded);
        return employeeToBeAdded;
    }

    private Long generateId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(0L) + 1;
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee employeeToBeUpdated = employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
        employeeToBeUpdated.setAge(updatedEmployee.getAge() != null ? updatedEmployee.getAge() : employeeToBeUpdated.getAge());
        employeeToBeUpdated.setSalary(updatedEmployee.getSalary() != null ? updatedEmployee.getSalary() : employeeToBeUpdated.getSalary());
        return employeeToBeUpdated;
    }

    public boolean deleteEmployee(Long id) {
        return employees.remove(employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new));
    }

    public List<Employee> listEmployeesByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
