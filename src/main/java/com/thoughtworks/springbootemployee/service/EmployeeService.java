package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        if (employee.hasInvalidAge(employee)) {
            throw new EmployeeCreateException();
        }
        employee.setActiveStatus(Boolean.TRUE);
        return employeeRepository.addEmployee(employee);
    }

    public void deleteEmployee(Long id) {
        Employee matchedEmployee = employeeRepository.findEmployeeById(id);
        matchedEmployee.setActiveStatus(Boolean.FALSE);
        employeeRepository.updateEmployee(id, matchedEmployee);
    }
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.updateEmployee(id, updatedEmployee);
    }

    public List<Employee> findAllEmployees() {
        return employeeRepository.listAllEmployees();
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findEmployeeByGender(gender);
    }

    public List<Employee> listEmployeesByPage(Long pageNumber, Long pageSize) {
        return employeeRepository.listEmployeesByPage(pageNumber, pageSize);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.findEmployeeById(id);
    }
}
