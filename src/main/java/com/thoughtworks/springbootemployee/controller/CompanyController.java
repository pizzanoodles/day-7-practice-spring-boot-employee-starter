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
    public List<Company> listCompaniesByPage(@RequestParam Long pageNumber, @RequestParam Long pageSize) {
        return companyRepository.listByPage(pageNumber, pageSize);
    }
    @PostMapping
    public Company addCompany(@RequestBody Company company) {
        return companyRepository.addCompany(company);
    }
    @PutMapping("/updateCompany/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company updatedCompany) {
        return companyRepository.updateCompany(id, updatedCompany);
    }
    @DeleteMapping("/deleteCompany/{id}")
    public String deleteCompany(@PathVariable Long id) {
        if(!companyRepository.deleteCompany(id)) {
            throw new CompanyNotFoundException();
        }
        return "Successfully deleted.";
    }
}
