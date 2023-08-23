package com.thoughtworks.springbootemployee.entity;

public class Employee {
    private final Long id; //TODO: change id to employeeId
    private Long companyId;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    //TODO: not recommended to use primitive types
    public Employee(long id, Long companyId, String name, Integer age, String gender, Integer salary) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    //TODO: removed unused methods
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
}
