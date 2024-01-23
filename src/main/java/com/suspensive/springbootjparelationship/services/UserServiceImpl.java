package com.suspensive.springbootjparelationship.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suspensive.springbootjparelationship.models.entities.Note;
import com.suspensive.springbootjparelationship.models.entities.User;

import com.suspensive.springbootjparelationship.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<User> getUsers() {
        List<User> users= (List<User>) userRepository.findAll();
        users.stream().forEach(u->{
            Hibernate.initialize(u.getNotes());
        });
        return users;
    }

    @Override
    @Transactional
    public User createUser(User user){
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean addNote(Long id, Note note) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresentOrElse(user ->{
            user.getNotes().add(note);
            userRepository.save(user);
        }
        , null);
         return optionalUser.orElseThrow().getNotes().contains(note);
    }

    @Override
    public List<Note> getUserNotes(Long id) {
        return userRepository.getAllNotes(id);
    }

    @Override
    public Map<String,List<String>> filterAllUserTags(Long id) {
        return Collections.singletonMap("tags", userRepository.getAllDistinctTags(id)) ;
    }  
}
