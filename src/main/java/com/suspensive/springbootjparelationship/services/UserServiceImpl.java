package com.suspensive.springbootjparelationship.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suspensive.springbootjparelationship.models.entities.Note;
import com.suspensive.springbootjparelationship.models.entities.User;
import com.suspensive.springbootjparelationship.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository repository;

    @Override
    @Transactional
    public List<User> getUsers() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional
    public User createUser(User user){
        return repository.save(user);
    }

    @Override
    @Transactional
    public Note addNote(Long id, Note note) {
        Optional<User> optionalUser = repository.findById(id);
        optionalUser.ifPresentOrElse(user ->{
            user.getNotes().add(note);
            repository.save(user);
            System.out.println(user);
        }
        , null);
         return note;
    }
}
