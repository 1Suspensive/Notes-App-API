package com.suspensive.springbootjparelationship.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.suspensive.springbootjparelationship.models.entities.Note;
import com.suspensive.springbootjparelationship.models.entities.User;


public interface UserRepository extends CrudRepository<User,Long>{

    @Query("SELECT u.notes from com.suspensive.springbootjparelationship.models.entities.User u WHERE u.id=?1")
    List<Note> getAllNotes(Long id);

    @Query("SELECT DISTINCT(t.tag) from com.suspensive.springbootjparelationship.models.entities.User u  JOIN u.notes n JOIN n.tags t WHERE u.id=?1")
    List<String> getAllDistinctTags(Long id);
}
