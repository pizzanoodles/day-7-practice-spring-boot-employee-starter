package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void should_return_first_two_companies_when_get_companies_given_pageNumber_and_pageSize_and_some_companies() {
        //given
        Company company1 = new Company(1L, "Logitech");
        Company company2 = new Company(2L, "Razer");
        Company company3 = new Company(3L, "Asus");
        List<Company> companies = List.of(company1, company2, company3);
        List<Company> firstTwoCompanies = List.of(company1, company2);
        Long pageNumber = 1L;
        Long pageSize = 2L;
        when(companyRepository.listCompaniesByPage(pageNumber, pageSize)).thenReturn(firstTwoCompanies);
        //when
        List<Company> companiesByPageResponse = companyService.getCompaniesByPage(pageNumber, pageSize);
        //then
        assertNotEquals(companies, companiesByPageResponse);
        assertEquals(firstTwoCompanies, companiesByPageResponse);
    }

    @Test
    void should_return_new_company_when_post_companies_given_new_company() {
        //given
        Company newCompanyToAdd = new Company(null, "PLDT");
        Company addedCompany = new Company(1L, "PLDT");
        when(companyRepository.addCompany(newCompanyToAdd)).thenReturn(addedCompany);
        //when
        Company companyAddedResponse = companyService.createCompany(newCompanyToAdd);
        //then
        assertEquals(addedCompany, companyAddedResponse);
    }

    @Test
    void should_return_updated_company_when_update_company_given_updated_company_info() {
        //given
        Company oldCompany = new Company(1L, "Asus");
        Company newCompany = new Company(1L, "Asrock");
        when(companyRepository.updateCompany(oldCompany.getId(), newCompany)).thenReturn(newCompany);
        //when
        Company updatedCompanyResponse = companyService.updateCompany(oldCompany.getId(), newCompany);
        //then
        assertEquals(newCompany, updatedCompanyResponse);
    }
}
