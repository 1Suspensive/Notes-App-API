package com.suspensive.springbootjparelationship.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/user")
@Tag(name = "Users", description = "Controller for Users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(
            summary = "Get all users",
            description = "Get all users from the database",
            tags = {"Users"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of users",
                            content = @Content(
                                    mediaType = "application/json",
                                     array= @ArraySchema(
                                            schema = @Schema(implementation = User.class)
                                    )
                            )
                    )
            }
    )
    @GetMapping("/users")
    public List<User> users() {
        return service.getUsers();
    }

    @Operation(
            summary = "Create user",
            description = "This endpoint creates a user with name and lastname",
            tags = {"Users"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description="User with name and lastname",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(
                                    value = "{\"id\":null,\"name\":\"John\",\"lastname\":\"Doe\",\"notes\":null}"
                    )
                )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Map.class)
                            )
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> create(@RequestBody User user){
        User userCreated = service.createUser(user);
        if(userCreated != null){
            return new ResponseEntity<>(Collections.singletonMap("message", "User created successfully"),null, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(Collections.singletonMap("message","An error occurred creating the user"),null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
