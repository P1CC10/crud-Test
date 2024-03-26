package com.crudtest.crudTest;
import com.crudtest.crudTest.controllers.StudentController;
import com.crudtest.crudTest.entities.Student;
import com.crudtest.crudTest.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerTest {
    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    @Order(1)
    void createStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("ivan");
        student.setLastName("piccioni");
        student.setWorking(true);

        String studentJSON = objectMapper.writeValueAsString(student);

        MvcResult resultActions = this.mockMvc.perform(post("/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJSON)).andDo(print())
                .andExpect(status().isOk()).andReturn();

    }

    @Test
    @Order(2)
    void updateStudentById() throws Exception {
        Long studentId = 1L;
        Student updatedStudent = new Student(studentId, "Updated", "Name", true);
        String studentJSON = objectMapper.writeValueAsString(updatedStudent);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.put("/v1/putstudent/{id}", studentId)
                        .contentType(MediaType.APPLICATION_JSON).content(studentJSON))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        Assertions.assertNotNull(content);
    }

    @Test
    @Order(4)
    void getAllStudents() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/v1/getall"))
                .andDo(print()).andReturn();

        List<Student> userFromResponseList = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(userFromResponseList.size()).isNotZero();


    }

    @Test
    @Order(3)
    void getStudent() throws Exception {
        Long studentId = 1L;


        MvcResult resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/v1/getsingle/{id}", studentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(studentId)).andReturn();

    }



    @Test
    @Order(6)
    void deleteStudente() throws Exception {
        Long studentId = 1L;


        MvcResult result = mockMvc.perform(delete("/v1/deletesingle/{id}",studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


    }

    @Test
    @Order(5)
    void studentActivation() throws Exception {
        Long id = 1L;

        boolean isWorking = true;

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/active/{id}",id)
                        .param("isWorking", String.valueOf(isWorking)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
