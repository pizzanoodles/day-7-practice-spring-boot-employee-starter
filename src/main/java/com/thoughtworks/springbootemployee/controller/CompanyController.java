package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
