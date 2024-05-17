package com.example.demo1222.controller;

import com.example.demo1222.dto.aim.EditAim;
import com.example.demo1222.service.AimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AimController {

    private final AimService aimService;

    @GetMapping("/api/aim/getAimList")
    public ResponseEntity<?> getAimList(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(aimService.getAimList(userId));
    }

    @GetMapping("/api/aim/chooseAim")
    public ResponseEntity<?> chooseAim(@RequestParam("userId") Long userId,
                                       @RequestParam("aimName") String name
                                       ){
        aimService.chooseAim(userId,name);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/api/aim/editAim")
    public ResponseEntity<?> editAim(@RequestBody EditAim editAim){
        aimService.editAim(editAim);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
