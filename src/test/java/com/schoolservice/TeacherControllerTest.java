package com.schoolservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolservice.controller.TeacherController;
import com.schoolservice.dto.request.TeacherRequestDto;
import com.schoolservice.dto.response.TeacherResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TeacherControllerTest {

    private static final String TEACHER_CONTROLLER_GENERAL_PATH = "/teacher/";

    private static final Long TEACHER_CONTROLLER_ID = 2L;

    @InjectMocks
    private TeacherController teacherController;

    @Mock
    private TeacherService teacherServiceMock;

    private MockMvc mockMvc;

    private TeacherRequestDto teacherRequestDto;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {

        teacherRequestDto = TeacherRequestDto.builder()
                .userName("testUserName")
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail@gm.com")
                .dateOfBirth(LocalDate.of(2011, 1, 1))
                .build();

        //add controller advice for negative cases
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    public void retrieveTeacher_passTeacherId_ShouldReturnOkHttpStatusWithRetirevedTeacher() throws Exception {
        TeacherResponseDto teacherResponseExpected = TeacherResponseDto.builder()
                .id(TEACHER_CONTROLLER_ID)
                .userName("testUserName")
                .firstName("testFirstName")
                .lastName("testLastName")
                .email("testEmail@gm.com")
                .dateOfBirth(LocalDate.of(2011, 1, 1))
                .build();

        when(teacherServiceMock.retrieveTeacher(TEACHER_CONTROLLER_ID)).thenReturn(teacherResponseExpected);
        MvcResult mvcResult = mockMvc.perform(get(TEACHER_CONTROLLER_GENERAL_PATH + TEACHER_CONTROLLER_ID))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();
        TeacherResponseDto teacherResponseActual = objectMapper.readValue(responseString, TeacherResponseDto.class);

        assertEquals(teacherResponseExpected, teacherResponseActual);
    }

    @Test
    public void deleteTeacher_passTeacherId_ShouldReturnOkHttpStatus() throws Exception {
        mockMvc.perform(delete(TEACHER_CONTROLLER_GENERAL_PATH + TEACHER_CONTROLLER_ID))
                .andExpect(status().isOk());

        verify(teacherServiceMock).deleteTeacher(TEACHER_CONTROLLER_ID);
    }

    @Test
    public void updateTeacher_passTeacherIdAndValidRequestDto_ShouldReturnOkHttpStatus() throws Exception {
        mockMvc.perform(put(TEACHER_CONTROLLER_GENERAL_PATH + TEACHER_CONTROLLER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherRequestDto)))
                .andExpect(status().isOk());

        verify(teacherServiceMock).updateTeacher(TEACHER_CONTROLLER_ID, teacherRequestDto);
    }

    @Test
    public void createTeacher_passValidRequestDto_ShouldReturnCreatedHttpStatus() throws Exception {
        mockMvc.perform(post(TEACHER_CONTROLLER_GENERAL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(teacherRequestDto)))
                .andExpect(status().isCreated());

        verify(teacherServiceMock).createTeacher(teacherRequestDto);
    }
}
