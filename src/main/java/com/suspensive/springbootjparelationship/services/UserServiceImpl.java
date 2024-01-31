package com.suspensive.springbootjparelationship.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suspensive.springbootjparelationship.models.entities.Note;
import com.suspensive.springbootjparelationship.models.entities.Tag;
import com.suspensive.springbootjparelationship.models.entities.User;
import com.suspensive.springbootjparelationship.models.exceptions.NoteNotFoundException;
import com.suspensive.springbootjparelationship.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<User> getUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        users.stream().forEach(u -> {
            Hibernate.initialize(u.getNotes());
        });
        return users;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean addNote(Long id, Note note) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresentOrElse(user -> {
            user.getNotes().add(note);
            userRepository.save(user);
        }, null);
        return optionalUser.orElseThrow().getNotes().contains(note);
    }

    @Override
    @Transactional
    public List<Note> getUserNotes(Long id) {
        List<Note> notes = userRepository.getAllNotes(id);
        return notes;
    }

    @Override
    @Transactional
    public Map<String, List<String>> filterAllUserTags(Long id) {
        return Collections.singletonMap("tags", userRepository.getAllDistinctTags(id));
    }

    @Override
    @Transactional
    public void editNote(Long id, Long noteId, Note note) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresentOrElse(u -> {
            List<Note> notes = u.getNotes();
            // First we search the note index using binary search
            int noteIndex = getNoteIndexByBinarySearch(notes, noteId);
            if (noteIndex != -1) {
                Note beforeNote = notes.get(noteIndex);
                beforeNote.setTittle(note.getTittle());
                beforeNote.setContent(note.getContent());
                editTags(beforeNote.getTags(), note.getTags());
                userRepository.save(u);
            } else {
                try {
                    throw new NoteNotFoundException();
                } catch (NoteNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

    @Transactional
    public void deleteNote(Long id, Long noteId){
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresentOrElse(user ->{
            List<Note> notes = user.getNotes();
            int noteIndex = getNoteIndexByBinarySearch(notes, noteId);
            if(noteIndex!= -1){
                notes.remove(noteIndex);
                userRepository.save(user);
            }else{
                try {
                    throw new NoteNotFoundException();
                } catch (NoteNotFoundException e) {
                    e.printStackTrace();
                }
            }
            
        }, null);
    }

    
    private void editTags(List<Tag> beforeTags, List<Tag> newTags){
        boolean changed = false;
        if (beforeTags.size() != newTags.size()){
            changed = true;
        }
        if(changed == true){
            for(int i = 0;i<Math.max(beforeTags.size(), newTags.size());i++){
                if(beforeTags.size()>newTags.size()){
                    if(i>= newTags.size()-1){
                        beforeTags.remove(i);
                    }else{
                        beforeTags.get(i).setTag(newTags.get(i).getTag());
                    }
                }else{
                    if(i>= beforeTags.size()-1){
                        beforeTags.add(newTags.get(i));
                    }else{
                        beforeTags.get(i).setTag(newTags.get(i).getTag());
                    }
                }
            }
        }
    }

    private int getNoteIndexByBinarySearch(List<Note> noteList, Long elementIndex) {
        Long start = 0L;
        Long end = (long) (noteList.size() - 1);

        while (start <= end) {
            Long mid = start + (end - start) / 2;

            if (noteList.get(mid.intValue()).getId() == elementIndex) {
                return mid.intValue(); // Element found
            } else if (noteList.get(mid.intValue()).getId() < elementIndex) {
                start = mid + 1; // Search in the right half
            } else {
                end = mid - 1; // Search in the left half
            }
        }

        return -1; // Element not found
    }
}
