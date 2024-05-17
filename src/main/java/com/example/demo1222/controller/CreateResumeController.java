package com.example.demo1222.controller;

import com.example.demo1222.service.CreateResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CreateResumeController {

    private final CreateResumeService createResumeService;
    @GetMapping(path = "/api/createResume/getStudent")
    public ResponseEntity<?> getStudent(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(createResumeService.getStudent(userId));
    }

    @PostMapping(path = "api/createResume/uploadResume")
    public ResponseEntity<?> uploadFile(@RequestParam("userId") Long userId,
                                        @RequestParam("resume") MultipartFile resume){

        String filePath = createResumeService.uploadResume(resume,userId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
