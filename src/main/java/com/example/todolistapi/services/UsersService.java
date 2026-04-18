package com.example.todolistapi.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.todolistapi.entity.Users;
import com.example.todolistapi.repository.UsersRepository;

@Service
public class UsersService {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final JwtService jwtService;

    public UsersService(PasswordEncoder passwordEncoder, UsersRepository repository, JwtService service) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = repository;
        this.jwtService = service;
    }

    public Users signUp(Users user) {
        Users newUser = new Users();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword_hash(passwordEncoder.encode(user.getPassword_hash()));
        return newUser;
    }

    public String login(Users user) {
        String email = user.getEmail();
        Users userFetched = usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (!passwordEncoder.matches(user.getPassword_hash(), userFetched.getPassword_hash())) {
            IO.println("Wrong Credentials");
            throw new RuntimeException("invalid Credentials");
        }
        return jwtService.generateToken(user);
    }
}