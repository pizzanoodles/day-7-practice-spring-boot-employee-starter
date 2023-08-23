package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee.hasInvalidAge(employee)) {
            throw new EmployeeCreateException();
        }
        employee.setActiveStatus(Boolean.TRUE);
        return employeeRepository.addEmployee(employee);
    }

    public void delete(Long id) {
        Employee matchedEmployee = employeeRepository.findEmployeeById(id);
        matchedEmployee.setActiveStatus(Boolean.FALSE);
        employeeRepository.updateEmployee(id, matchedEmployee);
    }
}
