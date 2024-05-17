package com.example.demo1222.controller;

import com.example.demo1222.Entity.RequestGrade;
import com.example.demo1222.Exception.RequestGradeExistException;
import com.example.demo1222.dto.GradeRequest;
import com.example.demo1222.dto.userData.SemesterResponse;
import com.example.demo1222.service.GoalService;
import com.example.demo1222.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class GoalController {

    private final UserService userService;
    private final GoalService goalService;

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

    @GetMapping(path = "/goal/getSubjectAndAvgGrade")
    public ResponseEntity<?> getSubjectAndAvgGrade(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(goalService.getSubjectAndAvgGrade(userId));
    }

    @GetMapping(path="/goal/getCombinedAvgGradeFirstPage")
    public ResponseEntity<?> getCombinedGradeFirstPage(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(goalService.getAvgCombinedGradeFirstPage(userId,1L));
    }

    @GetMapping(path = "/goal/getCombinedAvgGrade")
    public ResponseEntity<?> getCombinedGrade(@RequestParam("subjectName") String subjectName,
                                              @RequestParam("userId") Long userId){

        return ResponseEntity.ok(goalService.getAvgCombinedGrade(userId,1L,subjectName));
    }

    @GetMapping(path = "/goal/getRequestGrade")
    public ResponseEntity<?> getRequestGrade(@RequestParam("requestGrade") int requestGrade,
                                             @RequestParam("userId") Long userId,
                                             @RequestParam("currentSubject") String currentSubject){
        if(currentSubject.equals("null")) {
            return ResponseEntity.ok(goalService.getRequestGradeFirstPage(userId, requestGrade));
        }else{
            return ResponseEntity.ok(goalService.getRequestGradeFirstPage(userId, requestGrade,currentSubject));
        }
    }

    @PostMapping(path = "/goal/postRequestGrade")
    public ResponseEntity<?> postRequestGrade(@RequestBody GradeRequest gradeRequest){
        try {
            goalService.postRequestGrade(gradeRequest.getUserId(), gradeRequest.getGrade(), gradeRequest.getSubjectName());
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (RequestGradeExistException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Вы уже отправляли подобный запрос");
        }
    }



}
