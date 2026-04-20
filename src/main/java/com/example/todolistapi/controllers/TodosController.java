package com.example.todolistapi.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Map<String,Object> getAll(
        @RequestParam(defaultValue= "1") Integer page,
        @RequestParam(defaultValue = "2") Integer limit
    ) {
        List<Todos>arr = this.todosRepository.getSelected(limit, (page-1)*limit);
        Map<String, Object>map = new HashMap<>();
        map.put("data", arr);
        map.put("page", page);
        map.put("limit", limit);
        map.put("total", arr.size());
        return map;
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

    @PutMapping("/todos/{id}")
    public ResponseEntity<Todos> updateTodo(@Valid @RequestBody Todos todo, @PathVariable Integer id) {
        Todos fetchedTodo = todosRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        fetchedTodo.setTitle(todo.getTitle());
        fetchedTodo.setDescription(todo.getDescription());
        todosRepository.save(fetchedTodo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(fetchedTodo);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Todos> deleteTodo(@PathVariable Integer id) {
        Todos fetchedTodo = todosRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        todosRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(fetchedTodo);
    }
}