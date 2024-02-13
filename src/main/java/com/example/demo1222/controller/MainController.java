package com.example.demo1222.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/unsecured")
    public String unsecuredData(){
        return "Unsecured data";
    }

    @GetMapping("/secured")
    public String securedData(){
        return "Unsecured data";
    }

    @GetMapping("/info")
    public String userData(Principal principal){
        return principal.getName();
    }
    @GetMapping("/admin")
    public String hasAdmin(Principal principal){
        return principal.getName();
    }
}
