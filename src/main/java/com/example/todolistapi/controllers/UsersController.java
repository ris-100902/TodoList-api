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
import com.example.todolistapi.services.UsersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UsersController {
    private final UsersRepository usersRespository;
    private final UsersService usersService;

    public UsersController (UsersRepository usersRespository, UsersService usersService) {
        this.usersRespository = usersRespository;
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users inputUser) {
        String token = this.usersService.login(inputUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("{\"token\": "+ token +"}");
    }

    @PostMapping("/register")
    public ResponseEntity<Users> addUser(@Valid @RequestBody Users user) {
        Users newUser = this.usersService.signUp(user);
        if (newUser == null) throw new RuntimeException("Invalid User");
        usersRespository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/users")
    public Iterable<Users> getAllUsers(){
        return this.usersRespository.findAll();
    }
}