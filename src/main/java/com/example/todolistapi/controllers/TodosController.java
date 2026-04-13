package com.example.todolistapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todolistapi.entity.Todos;
import com.example.todolistapi.repository.TodosRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TodosController{
    private final TodosRepository todosRepository;

    public TodosController(TodosRepository repository) {
        this.todosRepository = repository;
    }

    @GetMapping("/todos")
    public Iterable<Todos> getAll() {
        return this.todosRepository.findAll();
    }

    @GetMapping(path="/todos/{id}")
    public ResponseEntity<Todos> getOne(@PathVariable Integer id) {
        Todos todo = todosRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return ResponseEntity.status(HttpStatus.OK).body(todo);
    }

    @PostMapping("/todos")
    public ResponseEntity<Todos> addTodo(@Valid @RequestBody Todos todo) {
        Todos newTodo = this.todosRepository.save(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }
}