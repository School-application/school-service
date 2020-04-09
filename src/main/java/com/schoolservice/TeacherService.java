package com.schoolservice;

import com.schoolservice.dto.request.TeacherRequestDto;
import com.schoolservice.dto.response.TeacherResponseDto;

public interface TeacherService {

    TeacherResponseDto retrieveTeacher(Long id);

    void deleteTeacher(Long id);

    void updateTeacher(Long id, TeacherRequestDto teacherRequestDto);

    void createTeacher(TeacherRequestDto teacherRequestDto);
}
