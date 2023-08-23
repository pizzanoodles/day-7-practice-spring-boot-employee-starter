package com.thoughtworks.springbootemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeApiTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MockMvc mockMvcClient;

    @BeforeEach
    void cleanupEmployeeData() {
        employeeRepository.cleanAll();
    }

    @Test
    void should_return_list_of_given_employees_when_get_request_all_employees_given_some_employees() throws Exception {
        //given
        Employee employeeJens = employeeRepository.addEmployee(new Employee(0L, 2L, "Jens", 23, "Male", 123123));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(employeeJens.getId()))
                .andExpect(jsonPath("$[0].name").value(employeeJens.getName()))
                .andExpect(jsonPath("$[0].age").value(employeeJens.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employeeJens.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employeeJens.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employeeJens.getCompanyId()));
    }

    @Test
    void should_return_employee_when_get_employee_given_employee_id() throws Exception {
        //given
        Employee employeeJens = employeeRepository.addEmployee(new Employee(0L, 2L, "Jens", 23, "Male", 123123));
        employeeRepository.addEmployee(new Employee(0L, 2L, "Ron", 23, "Male", 456456));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + employeeJens.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeJens.getId()))
                .andExpect(jsonPath("$.name").value(employeeJens.getName()))
                .andExpect(jsonPath("$.age").value(employeeJens.getAge()))
                .andExpect(jsonPath("$.salary").value(employeeJens.getSalary()))
                .andExpect(jsonPath("$.companyId").value(employeeJens.getCompanyId()));
    }

    @Test
    void should_return_404_not_found_when_get_employee__given_invalid_id() throws Exception {
        //given
        employeeRepository.addEmployee(new Employee(0L, 2L, "Jens", 23, "Male", 123123));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_list_of_employees_with_given_gender_when_get_employees_given_gender() throws Exception {
        //given
        Employee employeeJens = employeeRepository.addEmployee(new Employee(0L, 2L, "Jens", 23, "Male", 123123));
        employeeRepository.addEmployee(new Employee(0L, 2L, "Angelica", 23, "Female", 456456));
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender", "Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(employeeJens.getId()))
                .andExpect(jsonPath("$[0].name").value(employeeJens.getName()))
                .andExpect(jsonPath("$[0].age").value(employeeJens.getAge()))
                .andExpect(jsonPath("$[0].gender").value(employeeJens.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employeeJens.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employeeJens.getCompanyId()));
    }

    @Test
    void should_return_new_employee_when_post_employee_given_new_employee_JSON_format() throws Exception {
        //given
        Employee newEmployee = new Employee(0L, 3L, "Itachi", 23, "Male", 0);
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(notNullValue()))
                .andExpect(jsonPath("$.name").value(newEmployee.getName()))
                .andExpect(jsonPath("$.age").value(newEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(newEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployee.getSalary()));
    }

    @Test
    void should_return_no_content_status_when_delete_employees_given_employee_id_to_be_deleted() throws Exception {
        //given
        Employee employeeJens = employeeRepository.addEmployee(new Employee(0L, 2L, "Jens", 23, "Male", 123123));
        Long employeeIdToBeDeleted = 1L;
        //when
        mockMvcClient.perform(MockMvcRequestBuilders.delete("/employees/" + employeeIdToBeDeleted))
                .andExpect(status().isNoContent());
    }
}
