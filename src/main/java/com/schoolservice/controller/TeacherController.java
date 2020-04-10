package com.schoolservice.controller;

import com.schoolservice.service.TeacherService;
import com.schoolservice.dto.request.TeacherRequestDto;
import com.schoolservice.dto.response.TeacherResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping(value = "{id}")
    public TeacherResponseDto retrieveTeacher(@PathVariable Long id) {
        return teacherService.retrieveTeacher(id);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> updateTeacher(@PathVariable Long id, @RequestBody TeacherRequestDto teacherRequestDto) {
        teacherService.updateTeacher(id, teacherRequestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createTeacher(@RequestBody @Valid TeacherRequestDto teacherRequestDto) {
        teacherService.createTeacher(teacherRequestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
