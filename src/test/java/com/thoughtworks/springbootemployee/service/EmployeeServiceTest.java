package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeCreateException;
import com.thoughtworks.springbootemployee.exception.InactiveEmployeeException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Employee employeeResponse = employeeService.createEmployee(employee);

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
            employeeService.createEmployee(employee);
        });
        //then
        assertEquals("Employee must be 18~65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_more_than_65() {
        //given
        Employee employee = new Employee(null, 2L, "Jens", 100, "Male", 1000);
        //when
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.createEmployee(employee);
        });
        //then
        assertEquals("Employee must be 18~65 years old", employeeCreateException.getMessage());
    }

    @Test
    void should_return_active_status_true_when_create_given_new_employee() {
        //given
        Employee employee = new Employee(null, 2L, "Jens", 23, "Male", 1000);
        Employee savedEmployee = new Employee(1L, 2L, "Jens", 23, "Male", 1000);
        savedEmployee.setActiveStatus(Boolean.TRUE);
        when(mockedEmployeeRepository.addEmployee(employee)).thenReturn(savedEmployee);
        //when
        Employee employeeResponse = employeeService.createEmployee(employee);

        //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Jens", employeeResponse.getName());
        assertEquals(23, employeeResponse.getAge());
        assertEquals("Male", employeeResponse.getGender());
        assertEquals(1000, employeeResponse.getSalary());
        assertTrue(employeeResponse.isActive());
    }

    @Test
    void should_return_false_when_delete_given_employee_id_to_be_deleted() {
        //given
        Employee employee = new Employee(1L, 2L, "Jens", 23, "Male", 1000);
        employee.setActiveStatus(Boolean.TRUE);
        when(mockedEmployeeRepository.findEmployeeById(employee.getId())).thenReturn(employee);
        //when
        employeeService.deleteEmployee(employee.getId());
        //then
        verify(mockedEmployeeRepository).updateEmployee(eq(employee.getId()), argThat(tempEmployee -> {
            assertFalse(tempEmployee.isActive());
            assertEquals("Jens", tempEmployee.getName());
            assertEquals(23, tempEmployee.getAge());
            assertEquals("Male", tempEmployee.getGender());
            assertEquals(1000, tempEmployee.getSalary());
            return true;
        }));
    }

    @Test
    void should_return_updated_employee_when_update_employees_given_employee_id_and_updated_employee() {
        //given
        Employee oldEmployee = new Employee(1L, 2L, "Jens", 23, "Male", 1000);
        Employee updatedEmployee = new Employee(1L, 2L, "Jens", 72, "Male", 2000000);
        updatedEmployee.setActiveStatus(Boolean.TRUE);
        when(mockedEmployeeRepository.updateEmployee(oldEmployee.getId(), updatedEmployee)).thenReturn(updatedEmployee);
        when(mockedEmployeeRepository.findEmployeeById(updatedEmployee.getId())).thenReturn(updatedEmployee);
        //when
        Employee updateResponse = employeeService.updateEmployee(oldEmployee.getId(), updatedEmployee);
        //then
        assertNotNull(updateResponse);
    }

    @Test
    void should_return_list_of_employees_when_get_employees_given_some_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, 2L, "Tobirama", 42, "Male", 2000));
        employees.add(new Employee(2L, 2L, "Tsunade", 65, "Female", 15000));
        employees.add(new Employee(3L, 1L, "Madara", 39, "Male", 0));
        when(mockedEmployeeRepository.listAllEmployees()).thenReturn(employees);
        //when
        List<Employee> employeesResponse = employeeService.findAllEmployees();
        //then
        assertEquals(employees, employeesResponse);
    }

    @Test
    void should_return_list_of_employees_by_gender_when_get_employees_given_specific_gender_and_some_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, 2L, "Tobirama", 42, "Male", 2000));
        employees.add(new Employee(2L, 2L, "Tsunade", 65, "Female", 15000));
        employees.add(new Employee(3L, 1L, "Madara", 39, "Male", 0));
        List<Employee> femaleEmployees = new ArrayList<>();
        employees.add(new Employee(2L, 2L, "Tsunade", 65, "Female", 15000));
        when(mockedEmployeeRepository.findEmployeeByGender("Female")).thenReturn(femaleEmployees);
        when(mockedEmployeeRepository.listAllEmployees()).thenReturn(employees);
        //when
        List<Employee> employeeResponseFemales = employeeService.findEmployeesByGender("Female");
        List<Employee> employeeResponseMales = employeeService.findEmployeesByGender("Male");
        //then
        assertNotEquals(employees, employeeResponseFemales);
        assertNotEquals(employees, employeeResponseMales);
    }

    @Test
    void should_return_employees_by_page_when_get_employees_given_page_number_and_page_size_and_some_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employeeTobirama = new Employee(1L, 2L, "Tobirama", 42, "Male", 2000);
        Employee employeeTsunade = new Employee(2L, 2L, "Tsunade", 65, "Female", 15000);
        employees.add(employeeTobirama);
        employees.add(employeeTsunade);
        employees.add(new Employee(3L, 1L, "Madara", 39, "Male", 0));
        Long pageNumber = 1L;
        Long pageSize = 2L;
        List<Employee> firstTwoPageOne = List.of(employeeTobirama, employeeTsunade);
        when(mockedEmployeeRepository.listEmployeesByPage(pageNumber, pageSize)).thenReturn(firstTwoPageOne);
        //when
        List<Employee> employeesByPage = employeeService.listEmployeesByPage(pageNumber, pageSize);
        //then
        assertEquals(firstTwoPageOne, employeesByPage);
    }

    @Test
    void should_return_employee_when_get_employee_given_employee_id() {
        //given
        Employee employee1 = new Employee(1L, 2L, "Ranni", 29, "Female", 25100);
        Employee employee2 = new Employee(2L, 1L, "Melina", 26, "Female", 19500);
        when(mockedEmployeeRepository.findEmployeeById(employee1.getId())).thenReturn(employee1);
        when(mockedEmployeeRepository.findEmployeeById(employee2.getId())).thenReturn(employee2);
        //when
        Employee findFirstEmployeeResponse = employeeService.findEmployeeById(employee1.getId());
        Employee findSecondEmployeeResponse = employeeService.findEmployeeById(employee2.getId());
        //then
        assertEquals(employee1.getName(), findFirstEmployeeResponse.getName());
        assertEquals(employee1.getAge(), findFirstEmployeeResponse.getAge());
        assertEquals(employee1.getGender(), findFirstEmployeeResponse.getGender());
        assertEquals(employee1.getSalary(), findFirstEmployeeResponse.getSalary());
        assertEquals(employee2.getName(), findSecondEmployeeResponse.getName());
        assertEquals(employee2.getAge(), findSecondEmployeeResponse.getAge());
        assertEquals(employee2.getGender(), findSecondEmployeeResponse.getGender());
        assertEquals(employee2.getSalary(), findSecondEmployeeResponse.getSalary());
    }

    @Test
    void should_throw_InactiveEmployeeException_when_update_employees_given_employee_id_of_inactive_employee() {
        //given
        Employee employee = new Employee(1L, 2L, "Jens", 23, "Male", 100000);
        Employee updatedEmployee = new Employee(1L, 2L, "Jens", 24, "Male", 150000);
        employee.setActiveStatus(Boolean.FALSE);
        when(mockedEmployeeRepository.findEmployeeById(employee.getId())).thenReturn(employee);
        //when
        InactiveEmployeeException inactiveEmployeeException = assertThrows(InactiveEmployeeException.class, () -> {
            employeeService.updateEmployee(employee.getId(), updatedEmployee);
        });
        //then
        assertEquals("Employee is inactive", inactiveEmployeeException.getMessage());
    }
}
