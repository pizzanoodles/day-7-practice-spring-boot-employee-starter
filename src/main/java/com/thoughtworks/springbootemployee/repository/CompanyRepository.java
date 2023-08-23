package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    public static final int INCREMENT = 1;
    public static final long STARTING_ID = 0L;
    @Autowired
    EmployeeRepository employeeRepository;
    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Orient Overseas Container Line"));
        companies.add(new Company(2L, "COSCO Shipping Lines"));
        companies.add(new Company(3L, "Jollibee Food Corporation"));
        companies.add(new Company(4L, "Advanced Micro Devices Inc."));
        companies.add(new Company(5L, "Nvidia Corporation"));
    }

    public List<Company> listAllCompanies() {
        return companies;
    }

    public Company getCompanyById(Long id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> getEmployeesByCompanyId(Long id) {
        return employeeRepository.listAllEmployees()
                .stream()
                .filter(employee -> employee.getCompanyId().equals(id))
                .collect(Collectors.toList());
    }

    public List<Company> listCompaniesByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - INCREMENT) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company addCompany(Company company) {
        Company companyToBeAdded = new Company(generateId(), company.getName());
        companies.add(companyToBeAdded);
        return companyToBeAdded;
    }

    private Long generateId() {
        return companies.stream()
                .mapToLong(Company::getId)
                .max()
                .orElse(STARTING_ID) + INCREMENT;
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        Company companyToBeUpdated = companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
        companyToBeUpdated.setName(updatedCompany.getName() != null ? updatedCompany.getName() : companyToBeUpdated.getName());
        return companyToBeUpdated;
    }

    public boolean deleteCompany(Long id) {
        return companies.remove(companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new));
    }
}
