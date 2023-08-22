package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employees")
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping()
    public List<Employee> listAllEmployees() {
        return employeeRepository.listAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id) {
        return employeeRepository.findEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> findEmployeeByGender(@RequestParam String gender) {
        return employeeRepository.findEmployeeByGender(gender);
    }

    @PostMapping()
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.addEmployee(employee);
    }

    @PutMapping("/updateEmployee/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        return employeeRepository.updateEmployee(id, updatedEmployee);
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        if (!employeeRepository.deleteEmployee(id)) {
            throw new EmployeeNotFoundException();
        }
        return "Successfully deleted.";
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Employee> listEmployeesByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return employeeRepository.listEmployeesByPage(pageNumber, pageSize);
    }
}
