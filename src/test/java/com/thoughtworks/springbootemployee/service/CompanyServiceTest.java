package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {
    private CompanyService companyService;
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);
        companyService = new CompanyService(companyRepository);
    }

    @Test
    void should_return_list_of_companies_when_get_companies_given_some_companies() {
        //given
        Company company1 = new Company(1L, "Logitech");
        Company company2 = new Company(2L, "Razer");
        List<Company> companies = List.of(company1, company2);
        when(companyRepository.listAllCompanies()).thenReturn(companies);
        //when
        List<Company> companiesResponse = companyService.listAllCompanies();
        //then
        assertEquals(companies, companiesResponse);
    }

    @Test
    void should_return_company_when_get_companies_given_specific_company_id() {
        //given
        Company company = new Company(1L, "Rakk Gaming Gears");
        when(companyRepository.getCompanyById(company.getId())).thenReturn(company);
        //when
        Company companyByIdResponse = companyService.getCompanyById(company.getId());
        //then
        assertEquals(company, companyByIdResponse);
    }

    @Test
    void should_return_list_of_employees_under_given_company_when_get_companies_employees_given_company_id() {
        //given
        Employee employee1 = new Employee(1L, 2L, "Mark", 24, "Male", 12000);
        Employee employee2 = new Employee(2L, 2L, "Joy", 22, "Female", 19000);
        List<Employee> employees = List.of(employee1, employee2);
        Company company = new Company(2L, "Shopee");
        when(companyRepository.getEmployeesByCompanyId(company.getId())).thenReturn(employees);
        //when
        List<Employee> employeesByCompanyIdResponse = companyService.getEmployeesByCompanyId(company.getId());
        //then
        assertEquals(employees, employeesByCompanyIdResponse);
    }
}
