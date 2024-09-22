package com.suspensive.springbootjparelationship.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.suspensive.springbootjparelationship.models.entities.User;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.suspensive.springbootjparelationship.models.entities.Note;
import com.suspensive.springbootjparelationship.services.UserService;



@RestController
@RequestMapping("/app/notes")
@Tag(name = "Notes", description = "Controller for Notes")
public class NoteController {

    private final UserService service;

    public NoteController(UserService service) {
        this.service = service;
    }

    @Operation(
            summary = "Add note",
            description = "Add a note to a user",
            tags = {"Notes"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description="Note with tittle, content and tags",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Note.class),
                            examples = @ExampleObject(
                                    value = "{\"id\":null,\"tittle\":\"Top 3 songs\",\"content\":\"Closer The Chainsmokers, IDFC, Hit'em up\",\"tags\":[{\"id\":null,\"tag\":\"music\"}]}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Note added successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\":\"Note added successfully!\"}")
                            )
                    )
            }
    )
    @PutMapping("/add/{id}")
    public ResponseEntity<Map<String,String>> addNote(@PathVariable("id") Long userId, @RequestBody Note note){
        if(service.addNote(userId, note)!=false){
            return new ResponseEntity<>(Collections.singletonMap("message", "Note added successfully!"),null,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(Collections.singletonMap("message", "Note could not be add."),null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get user notes",
            description = "Get all notes from a user given an id",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notes retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Note.class))
                            )
                    )
            }
    )
    public List<Note> getUserNotes(@PathVariable("id") Long id){
        return service.getUserNotes(id);
    }

    @GetMapping("/tags/{id}")
    @Operation(
            summary = "Get user tags",
            description = "Get all tags from a user given an id",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tags retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"tags\":[\"music, home, mathematics\"]}"
                                    )
                            )
                    )
            }
    )
    public Map<String,List<String>> filterAllTags(@PathVariable("id") Long id) {
        return service.filterAllUserTags(id);
    }

    @Operation(
            summary = "Edit note",
            description = "Edit a note from a user given an id",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note edited successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\":\"Note was edited successfully\"}"
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/edit/{id}")
    public ResponseEntity<Map<String,String>> edit(@PathVariable("id") Long id,@RequestParam Long noteId, @RequestBody Note note){
        service.editNote(id, noteId, note);
        return new ResponseEntity<>(Collections.singletonMap("message","Note was edited successfully"), null,HttpStatus.OK);
    }

    @Operation(
            summary = "Delete note",
            description = "Delete a note from a user given an id",
            tags = {"Notes"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Note edited successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            value = "{\"message\":\"Note was deleted successfully\"}"                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable("id") Long id, @RequestParam Long noteId){
        service.deleteNote(id, noteId);
        return ResponseEntity.ok().body(Collections.singletonMap("message","Note was deleted successfully"));
    }
}