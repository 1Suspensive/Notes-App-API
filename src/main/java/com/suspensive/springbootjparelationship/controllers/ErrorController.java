package com.suspensive.springbootjparelationship.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.suspensive.springbootjparelationship.models.dtos.ErrorDTO;
import com.suspensive.springbootjparelationship.models.exceptions.NoteNotFoundException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDTO> nullAttribute(){
        ErrorDTO nullError = new ErrorDTO();
        nullError.setError("Null Attributes");
        nullError.setMessage("Please complete all the fields");
        nullError.setStatus(500);
        return ResponseEntity.internalServerError().body(nullError);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ErrorDTO> userNotFound(){
        ErrorDTO userNotFindError = new ErrorDTO();
        userNotFindError.setError("User could not be found!");
        userNotFindError.setMessage("Please try again with an existing user");
        userNotFindError.setStatus(400);
        return ResponseEntity.badRequest().body(userNotFindError);
    }

    @ExceptionHandler({NoteNotFoundException.class})
    public ResponseEntity<ErrorDTO> noteNotFound(){
        ErrorDTO noteNotFoundError = new ErrorDTO();
        noteNotFoundError.setError("Note could not be found");
        noteNotFoundError.setMessage("Please try again with an existing note");
        noteNotFoundError.setStatus(400);
        return ResponseEntity.badRequest().body(noteNotFoundError);
    }

}