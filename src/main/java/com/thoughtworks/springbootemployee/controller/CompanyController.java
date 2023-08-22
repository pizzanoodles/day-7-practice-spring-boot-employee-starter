package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/companies")
@RestController
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;
    @GetMapping
    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }
    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyRepository.getCompanyById(id);
    }
    @GetMapping("/{id}/employees")
    public List<Employee> employeesbyCompany(@PathVariable Long id) {
        return companyRepository.getEmployeesByCompanyId(id);
    }
    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> listCompaniesByPage(@PathVariable Long pageNumber, @PathVariable Long pageSize) {
        return companyRepository.listByPage(pageNumber, pageSize);
    }
}
