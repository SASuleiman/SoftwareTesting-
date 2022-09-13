package org.suleiman.softwareTesting.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.suleiman.softwareTesting.Controller.StudentController;
import org.suleiman.softwareTesting.JPA.StudentEntity;
import org.suleiman.softwareTesting.Services.StudentServices;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentServices studentServices;

    @Test
    public void getallStudentsSuccessTest() throws Exception {
        // given
        List<StudentEntity> studentEntities = new ArrayList<>();
        studentEntities.add(getStudent1());
        studentEntities.add(getStudent2());

        // when
        Mockito.when(studentServices.listAllStudent()).thenReturn(studentEntities);

        // verify
       mockMvc.perform(MockMvcRequestBuilders
               .get("/api/getStudents")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$",hasSize(2)))
               .andExpect(jsonPath("$[1].name", Matchers.is("Jamil")));
    }

    @Test
    public void testSuccessfulGetStudentById() throws Exception {
        // given
        List<StudentEntity> studentEntities = new ArrayList<>();
        StudentEntity student = getStudent1();
        studentEntities.add(student);
        studentEntities.add(getStudent2());


        //when
        Mockito.when(studentServices.findByDepartment(student.getDepartment())).thenReturn(studentEntities);

        //assertions
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/api/getStudentByDepartment/MLS")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$[1].name", Matchers.is("Jamil")))
                .andExpect(jsonPath("$",hasSize(2)));

    }

    @Test
    public void testForSuccessfullyAddingStudent() throws Exception {
        StudentEntity student = getStudent1();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/addStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(student));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

    }

    @Test
    public void testToSuccessfullyDeleteEntity() throws Exception {
        StudentEntity student = getStudent1();

        MockHttpServletRequestBuilder mockBuilder = MockMvcRequestBuilders.delete("/api/deleteStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(student));
        mockMvc.perform(mockBuilder)
                .andExpect(status().isOk());

    }

    @Test
    public void successfulDeleteOfStudentUsingId() throws Exception {
        StudentEntity student = getStudent1();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/api/deleteStudent/1")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void successfulUpdateOfStudent() throws Exception {
        StudentEntity student = getStudent1();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/updateStudent/1")
                .contentType(MediaType.ALL)
                .accept(MediaType.ALL);

        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }

    private StudentEntity getStudent1() {
        StudentEntity student = new StudentEntity();
        student.setId(1L);
        student.setEmail("Jamila@gmail.com");
        student.setName("Jamila");
        student.setDob(LocalDate.of(2000, Month.AUGUST,23));
        student.setDepartment("MLS");
        return  student;
    }

    private StudentEntity getStudent2() {
        StudentEntity student = new StudentEntity();
        student.setId(1L);
        student.setEmail("Jamil@gmail.com");
        student.setName("Jamil");
        student.setDob(LocalDate.of(2000, Month.AUGUST,24));
        student.setDepartment("MLS");
        return  student;
    }

}
