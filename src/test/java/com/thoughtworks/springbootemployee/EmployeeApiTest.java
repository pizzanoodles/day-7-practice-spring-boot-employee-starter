package com.thoughtworks.springbootemployee;

import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
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
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id" ).value(employeeJens.getId()))
                .andExpect(jsonPath("$[0].name").value(employeeJens.getName()))
                .andExpect(jsonPath("$[0].age").value(employeeJens.getAge()))
                .andExpect(jsonPath("$[0].salary").value(employeeJens.getSalary()))
                .andExpect(jsonPath("$[0].companyId").value(employeeJens.getCompanyId()));
    }
}
