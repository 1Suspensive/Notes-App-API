package com.suspensive.springbootjparelationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.suspensive.springbootjparelationship.models.entities.User;

public interface UserRepository extends CrudRepository<User,Long>{

}
