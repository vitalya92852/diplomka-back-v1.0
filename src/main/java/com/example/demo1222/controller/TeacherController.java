package com.example.demo1222.controller;

import com.example.demo1222.dto.teacher.ChangeGradeRequest;
import com.example.demo1222.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping(path = "/api/teacher/getSubjects")
    public ResponseEntity<?> getSubjects(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(teacherService.getSubjects(userId));
    }

    @GetMapping(path = "/api/teacher/getStudents")
    public ResponseEntity<?> getStudents(@RequestParam("userId") Long userId,
                                         @RequestParam("subject") String subject
                                         ){
        return ResponseEntity.ok(teacherService.getStudents(userId,subject));
    }

    @GetMapping(path = "/api/teacher/getRequestGradeSubjects")
    public ResponseEntity<?> getRequestGradeSubjects(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(teacherService.getRequestGradeSubjects(userId));
    }

    @GetMapping(path = "/api/teacher/getStudentsRequest")
    public ResponseEntity<?> getRequestGradeStudents(@RequestParam("userId") Long userId,
                                         @RequestParam("subject") String subject
    ){
        return ResponseEntity.ok(teacherService.getStudentsRequest(userId,subject));
    }

    @PutMapping(path = "/api/teacher/changeGradeRequest")
    public ResponseEntity<?> rejectGradeRequest(@RequestBody ChangeGradeRequest changeGradeRequest){
        teacherService.rejectRequest(changeGradeRequest.getUserId(),changeGradeRequest.getStudentId(),changeGradeRequest.getRequestGrade(),changeGradeRequest.getSubjectName());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(path = "/api/teacher/acceptRequest")
    public ResponseEntity<?> acceotRequest(@RequestBody ChangeGradeRequest changeGradeRequest){
        teacherService.acceptRequest(changeGradeRequest.getUserId(),changeGradeRequest.getStudentId(),changeGradeRequest.getRequestGrade(),changeGradeRequest.getSubjectName());
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
