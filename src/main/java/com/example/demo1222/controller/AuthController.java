package com.example.demo1222.controller;

import com.example.demo1222.Entity.Group;
import com.example.demo1222.Entity.User;
import com.example.demo1222.Exception.AppErrorException;
import com.example.demo1222.Exception.UserNotFoundException;
import com.example.demo1222.XmlClasses.XMLParser;
import com.example.demo1222.dto.JwtSignInRequest;
import com.example.demo1222.dto.JwtSignInResponse;
import com.example.demo1222.dto.RegistrationUserDto;
import com.example.demo1222.dto.UserResponse;
import com.example.demo1222.jwt.JwtCore;
import com.example.demo1222.repositories.GroupRepository;
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
    private final XMLParser xmlParser;
    private final GroupRepository groupRepository;

    @Value("${auth.app.secret}")
    private String secret;

    @PostMapping(path = "/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtSignInRequest authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e){
            System.out.println(authRequest);
            return new ResponseEntity<>("Неверный логин или пароль",HttpStatus.UNAUTHORIZED);
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



        UserResponse userResponse = userService.getUserResponse(jwtToken,getUserName);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto){
        System.out.println(registrationUserDto);

        if(!userService.hasEmptyFields(registrationUserDto)){
            return new ResponseEntity<>("Не все поля заполнены",HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUsername(registrationUserDto.getLogin())){
            return new ResponseEntity<>("Данный пользователь уже существует",HttpStatus.BAD_REQUEST);
        }
        if(!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())){
            return new ResponseEntity<>("Пароли не совпадают", HttpStatus.BAD_REQUEST);
        }
        if(registrationUserDto.getPassword().length()<8){
            return new ResponseEntity<>("Пароль должен быть длинее 8 букв", HttpStatus.BAD_REQUEST);
        }

        userService.createNewUser(registrationUserDto);

        UserDetails userDetails = userService.loadUserByUsername(registrationUserDto.getLogin());

        String token = jwtCore.generateToken(userDetails);

        UserResponse userResponse = userService.getUserResponse(token,registrationUserDto.getLogin());

        return ResponseEntity.ok(userResponse);

    }

    @GetMapping(path = "/api/registration/getGroups")
    public String[] getGroups(){
        return  groupRepository.findAll().stream().map(Group::getName).toArray(String[]::new);
    }

}
