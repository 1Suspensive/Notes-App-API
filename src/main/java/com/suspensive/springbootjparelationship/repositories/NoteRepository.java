package com.suspensive.springbootjparelationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.suspensive.springbootjparelationship.models.entities.Note;

public interface NoteRepository extends CrudRepository<Note,Long>{

}
