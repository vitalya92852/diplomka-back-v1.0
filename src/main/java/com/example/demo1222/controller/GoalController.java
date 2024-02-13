package com.example.demo1222.controller;

import com.example.demo1222.dto.userData.SemesterResponse;
import com.example.demo1222.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class GoalController {

    private final UserService userService;

    @GetMapping(path = "/goal/goalAcademData")
    public ResponseEntity<Object> getGoalAcademData(@RequestParam("userId") Long userId) {
        SemesterResponse semester = userService.getGoalUserData(userId,1L);

        return ResponseEntity.ok(semester);
    }

    @GetMapping(path = "/goal/goalPractiseData")
    public ResponseEntity<Object> getGoalPractiselData(@RequestParam("userId") Long userId) {
        SemesterResponse semester = userService.getGoalUserData(userId,2L);

        return ResponseEntity.ok(semester);
    }

    @GetMapping(path = "/goal/getSubjects")
    public ResponseEntity<?> getSubjectList(@RequestParam("userId") Long userId){

        return ResponseEntity.ok(userService.getUserSubjects(userId));
    }

    @GetMapping(path = "/goal/changeAcademSubject")
    public ResponseEntity<?> changeAcademSubject(@RequestParam("academSubjectName") String academSubjectName,
                                                 @RequestParam("userId") Long userId){
        return ResponseEntity.ok(userService.changeSubject(userId,1L,academSubjectName));
    }

    @GetMapping(path = "/goal/changePractiseSubject")
    public ResponseEntity<?> changePractiseSubject(@RequestParam("practiseSubjectName") String practiseSubjectName,
                                                   @RequestParam("userId") Long userId){

        return ResponseEntity.ok(userService.changeSubject(userId,2L,practiseSubjectName));
    }



}
