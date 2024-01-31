package com.example.todo.management.service;

import com.example.todo.management.dto.TodoDto;
import com.example.todo.management.entity.Todo;
import com.example.todo.management.exception.ResourceNotFoundException;
import com.example.todo.management.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        //convert todoDto into Todo Jpa entity
//        Todo todo=new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());
        Todo todo = modelMapper.map(todoDto, Todo.class);
        //Todo jpa entity
        Todo saveTodo = todoRepository.save(todo);
        //Convert saved Todo jpa entity object into TodoDto object
//        TodoDto dto=new TodoDto();
//        dto.setId(saveTodo.getId());
//        dto.setTitle(saveTodo.getTitle());
//        dto.setDescription(saveTodo.getDescription());
//        dto.setCompleted(saveTodo.isCompleted());
        TodoDto saveTodoDto = modelMapper.map(saveTodo, TodoDto.class);
        return saveTodoDto;
    }

    @Override
    public TodoDto getTodo(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id" + id));

        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodo() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id" + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo todoUpdated = todoRepository.save(todo);
        return modelMapper.map(todoUpdated, TodoDto.class);
    }

    @Override
    public void delete(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id" + id));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id" + id));
        todo.setCompleted(Boolean.TRUE);
        Todo updateTodo = todoRepository.save(todo);
        return modelMapper.map(updateTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id" + id));
        todo.setCompleted(Boolean.FALSE);
        Todo updateTodo = todoRepository.save(todo);
        return modelMapper.map(updateTodo, TodoDto.class);
    }
}
