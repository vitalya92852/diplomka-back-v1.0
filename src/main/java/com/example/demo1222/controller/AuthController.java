package com.example.demo1222.controller;

import com.example.demo1222.Entity.User;
import com.example.demo1222.Exception.AppErrorException;
import com.example.demo1222.Exception.UserNotFoundException;
import com.example.demo1222.dto.JwtSignInRequest;
import com.example.demo1222.dto.JwtSignInResponse;
import com.example.demo1222.dto.UserResponse;
import com.example.demo1222.jwt.JwtCore;
import com.example.demo1222.repositories.UserRepository;
import com.example.demo1222.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Value("${auth.app.secret}")
    private String secret;

    @PostMapping(path = "/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtSignInRequest authRequest){
        System.out.println(authRequest+"123");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e){
            System.out.println(authRequest);
            return new ResponseEntity<>(new AppErrorException(HttpStatus.UNAUTHORIZED.value(),"Неверный логин или пароль"),HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        String token = jwtCore.generateToken(userDetails);

        UserResponse userResponse = userService.getUserResponse(token,authRequest.getUsername());

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token){
        String jwtToken = token.substring(6);
        String getUserName;
        try{
            jwtCore.validateToken(jwtToken);
        }catch (ExpiredJwtException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        getUserName = userRepository.findById(jwtCore.getUserId(jwtToken)).orElseThrow(null).getUsername();

//        UserDetails userDetails = userService.loadUserByUsername(getUserName);

        UserResponse userResponse = userService.getUserResponse(jwtToken,getUserName);

        return ResponseEntity.ok(userResponse);
    }

}
