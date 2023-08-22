package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();
    static {
        employees.add(new Employee(1L, "Alice", 30, "Female", 5000));
        employees.add(new Employee(2L, "Bob", 31, "Male", 6000));
        employees.add(new Employee(3L, "Carl", 32, "Male", 7000));
        employees.add(new Employee(4L, "David", 33, "Male", 8000));
        employees.add(new Employee(5L, "Elen", 34, "Female", 9000));
    }
    public List<Employee> listAll() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }
}
