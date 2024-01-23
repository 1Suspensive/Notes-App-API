package com.suspensive.springbootjparelationship.services;

import java.util.List;
import java.util.Map;

import com.suspensive.springbootjparelationship.models.entities.Note;
import com.suspensive.springbootjparelationship.models.entities.User;

public interface UserService {
    public List<User> getUsers();
    public User createUser(User user);
    public boolean addNote(Long id, Note note);
    public List<Note> getUserNotes(Long id);
    public Map<String, List<String>> filterAllUserTags(Long id);
}
