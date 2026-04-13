package com.example.todolistapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolistapi.entity.Todos;

public interface TodosRepository extends JpaRepository<Todos, Integer>{}