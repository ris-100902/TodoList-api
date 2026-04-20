package com.example.todolistapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.todolistapi.entity.Todos;

@Repository
public interface TodosRepository extends JpaRepository<Todos, Integer>{
    @Query(value="SELECT * FROM TODOS LIMIT :limit OFFSET :offset;", nativeQuery=true)
    List<Todos>getSelected(int limit, int offset);
}