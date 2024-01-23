package com.suspensive.springbootjparelationship.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suspensive.springbootjparelationship.models.entities.Note;
import com.suspensive.springbootjparelationship.services.UserService;



@RestController
@RequestMapping("/app/notes")
public class NoteController {

    @Autowired
    private UserService service;

    @PutMapping("/add/{id}")
    public ResponseEntity<Map<String,String>> addNote(@PathVariable Long id, @RequestBody Note note){
        if(service.addNote(id, note)!=false){
            return new ResponseEntity<Map<String,String>>(Collections.singletonMap("message", "Note added sucessfully!"),null,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<Map<String,String>>(Collections.singletonMap("message", "Note could not be add."),null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public List<Note> getUserNotes(@PathVariable Long id){
        return service.getUserNotes(id);
    }

    @GetMapping("/tags/{id}")
    public Map<String,List<String>> filterAllTags(@PathVariable Long id) {
        return service.filterAllUserTags(id);
    }
    
}