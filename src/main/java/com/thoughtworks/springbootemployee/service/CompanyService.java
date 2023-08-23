package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> listAllCompanies() {
        return companyRepository.listAllCompanies();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.getCompanyById(id);
    }

    public List<Employee> getEmployeesByCompanyId(Long id) {
        return companyRepository.getEmployeesByCompanyId(id);
    }

    public List<Company> getCompaniesByPage(Long pageNumber, Long pageSize) {
        return companyRepository.listCompaniesByPage(pageNumber, pageSize);
    }

    public Company createCompany(Company newCompanyToAdd) {
        return companyRepository.addCompany(newCompanyToAdd);
    }

    public Company updateCompany(Long id, Company newCompany) {
        return companyRepository.updateCompany(id, newCompany);
    }

    public boolean deleteCompany(Long id) {
        return companyRepository.deleteCompany(id);
    }
}
