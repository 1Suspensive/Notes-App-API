package com.suspensive.springbootjparelationship.services;

import java.util.List;

import com.suspensive.springbootjparelationship.models.entities.Note;
import com.suspensive.springbootjparelationship.models.entities.User;

public interface UserService {
    public List<User> getUsers();
    public User createUser(User user);
    public Note addNote(Long id, Note note);
}
