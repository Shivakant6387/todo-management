package com.example.todo.management.service;

import com.example.todo.management.dto.TodoDto;
import com.example.todo.management.entity.Todo;
import com.example.todo.management.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{
    private TodoRepository todoRepository;
    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        //convert todoDto into Todo Jpa entity
        Todo todo=new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        //Todo jpa entity
        Todo saveTodo=todoRepository.save(todo);
        //Convert saved Todo jpa entity object into TodoDto object
        TodoDto dto=new TodoDto();
        dto.setId(saveTodo.getId());
        dto.setTitle(saveTodo.getTitle());
        dto.setDescription(saveTodo.getDescription());
        dto.setCompleted(saveTodo.isCompleted());
        return dto;
    }
}
