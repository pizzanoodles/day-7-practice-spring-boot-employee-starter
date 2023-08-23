package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp() {
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    public void should_return_created_employee_when_create_given_employee_service_and_employee_with_valid_age() {
        //given
        Employee employee = new Employee(null, 2L, "Jens", 23, "Male", 1000);
        Employee savedEmployee = new Employee(1L, 2L, "Jens", 23, "Male", 1000);
        ;
        when(mockedEmployeeRepository.addEmployee(employee)).thenReturn(savedEmployee);
        //when
        Employee employeeResponse = employeeService.create(employee);

        //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Jens", employeeResponse.getName());
        assertEquals(23, employeeResponse.getAge());
        assertEquals("Male", employeeResponse.getGender());
        assertEquals(1000, employeeResponse.getSalary());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_less_than_18() {
        //given
        Employee employee = new Employee(null, 2L, "Jens", 17, "Male", 1000);
        //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });
        //then
        assertEquals("Employee must be 18~65 years old", employeeCreateException.getMessage());
    }
}
