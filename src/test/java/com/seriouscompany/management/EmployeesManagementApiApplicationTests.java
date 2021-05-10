package com.seriouscompany.management;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seriouscompany.management.api.model.EmployeeApiModel;
import com.seriouscompany.management.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeesManagementApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeAll
    public void init() {
        mapper.disable(MapperFeature.USE_ANNOTATIONS);
    }

    @AfterEach
    public void resetDB() {
        employeeRepository.deleteAll();
    }

    @Test
    public void employeeCreatedSuccess() throws Exception {
        EmployeeApiModel employee = new EmployeeApiModel("Alex", 27);

        createEmployee(employee)
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Alex")))
                .andExpect(jsonPath("$.age", is(27)))
                .andExpect(jsonPath("$.state", is("ADDED")));
    }

    @Test
    public void employeeCreationFailedValidation() throws Exception {

        EmployeeApiModel employee = new EmployeeApiModel();

        createEmployee(employee)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("400")))
                .andExpect(jsonPath("$.message", containsString("Validation failed")));
    }

    @Test
    public void employeeTransitionSuccess() throws Exception {
        EmployeeApiModel employee = new EmployeeApiModel("Alex", 27);

        String responseContent = createEmployee(employee).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        EmployeeApiModel employeeResponse = mapper.readValue(responseContent, EmployeeApiModel.class);

        mvc.perform(post("/api/employee/{id}/IN_CHECK", employeeResponse.getId()))
                .andExpect(jsonPath("$.state", is("IN_CHECK")));
    }

    @Test
    public void employeeTransitionFailed() throws Exception {
        EmployeeApiModel employee = new EmployeeApiModel("Alex", 27);

        String responseContent = createEmployee(employee).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        EmployeeApiModel employeeResponse = mapper.readValue(responseContent, EmployeeApiModel.class);

        mvc.perform(post("/api/employee/{id}/APPROVE", employeeResponse.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("400")));
    }

    private ResultActions createEmployee(EmployeeApiModel employee) throws Exception {
        ResultActions perform = mvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(employee)));
        return perform;
    }

}
