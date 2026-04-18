package com.example.todolistapi.repository;

import java.util.Optional;

import com.example.todolistapi.entity.Users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer>{
    @Query(value="SELECT * FROM USERS WHERE email=:email;", nativeQuery=true)
    Optional<Users> findByEmail(String email);
}