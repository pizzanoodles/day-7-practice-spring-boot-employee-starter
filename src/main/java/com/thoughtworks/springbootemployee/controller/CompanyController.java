package com.thoughtworks.springbootemployee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("companies")
@RestController
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;
}
