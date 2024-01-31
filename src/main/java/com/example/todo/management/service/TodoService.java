package com.example.todo.management.service;

import com.example.todo.management.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);

    TodoDto getTodo(long id);

    List<TodoDto> getAllTodo();

    TodoDto updateTodo(TodoDto todoDto, long id);

    void delete(long id);
}
