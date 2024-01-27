package com.example.todo.management.service;

import com.example.todo.management.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodo(long id);
    List<TodoDto> getAllTodo();
}
