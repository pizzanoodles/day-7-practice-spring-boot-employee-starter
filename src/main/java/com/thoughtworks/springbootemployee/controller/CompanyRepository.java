package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
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
}
