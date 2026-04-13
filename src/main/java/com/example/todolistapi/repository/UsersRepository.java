package com.example.todolistapi.repository;

import com.example.todolistapi.entity.Users;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Integer>{
    
}