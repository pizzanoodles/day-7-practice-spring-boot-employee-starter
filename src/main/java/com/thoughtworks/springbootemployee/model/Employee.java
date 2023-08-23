package com.thoughtworks.springbootemployee.model;

public class Employee {
    public static final int MIN_VALID_AGE = 18;
    public static final int MAX_VALID_AGE = 65;
    private final Long id;
    private Long companyId;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    public Employee(Long id, Long companyId, String name, Integer age, String gender, Integer salary) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    public void setSalary(Integer salary) {
        this.salary = salary;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public boolean hasInvalidAge(Employee employee) {
        return getAge() < MIN_VALID_AGE || employee.getAge() > MAX_VALID_AGE;
    }
}
