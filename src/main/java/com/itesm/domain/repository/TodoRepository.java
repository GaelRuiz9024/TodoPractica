package com.itesm.domain.repository;

import com.itesm.domain.models.Todo;

import java.util.List;
import java.util.UUID;

public interface TodoRepository {
    Todo save(Todo todo);
    List<Todo> findAllTodos();
    void delete(UUID id);
}
