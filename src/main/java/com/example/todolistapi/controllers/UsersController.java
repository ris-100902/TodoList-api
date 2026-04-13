package com.example.todolistapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolistapi.entity.Users;
import com.example.todolistapi.repository.UsersRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsersController {
    private final UsersRepository usersRespository;
    public UsersController (UsersRepository usersRespository) {
        this.usersRespository = usersRespository;
    }

    @PostMapping("/register")
    public ResponseEntity<Users> addUser(@Valid @RequestBody Users user) {
        Users newUser = usersRespository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/users")
    public Iterable<Users> getAllUsers(){
        return this.usersRespository.findAll();
    }
}