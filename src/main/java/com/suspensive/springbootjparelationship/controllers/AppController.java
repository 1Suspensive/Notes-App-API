package com.suspensive.springbootjparelationship.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.suspensive.springbootjparelationship.models.entities.User;
import com.suspensive.springbootjparelationship.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public List<User> users() {
        return service.getUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> create(@RequestBody User user){
        User userCreated = service.createUser(user);
        if(userCreated != null){
            return new ResponseEntity<Map<String,String>>(Collections.singletonMap("message", "User created sucessfully"),null, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<Map<String,String>>(Collections.singletonMap("message","An error ocurred creating the user"),null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
