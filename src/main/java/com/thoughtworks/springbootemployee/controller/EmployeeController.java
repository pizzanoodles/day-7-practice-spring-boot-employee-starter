package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SuppressWarnings("all")
@RequestMapping("/employees")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public List<Employee> listAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findEmployeeByGender(@RequestParam String gender) {
        return employeeService.findEmployeesByGender(gender);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployee(id, updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listEmployeesByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return employeeService.listEmployeesByPage(pageNumber, pageSize);
    }
}
