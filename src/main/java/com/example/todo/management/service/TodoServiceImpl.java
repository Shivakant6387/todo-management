package com.example.todo.management.service;

import com.example.todo.management.dto.TodoDto;
import com.example.todo.management.entity.Todo;
import com.example.todo.management.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        //convert todoDto into Todo Jpa entity
//        Todo todo=new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());
        Todo todo=modelMapper.map(todoDto,Todo.class);
        //Todo jpa entity
        Todo saveTodo=todoRepository.save(todo);
        //Convert saved Todo jpa entity object into TodoDto object
//        TodoDto dto=new TodoDto();
//        dto.setId(saveTodo.getId());
//        dto.setTitle(saveTodo.getTitle());
//        dto.setDescription(saveTodo.getDescription());
//        dto.setCompleted(saveTodo.isCompleted());
        TodoDto saveTodoDto=modelMapper.map(saveTodo,TodoDto.class);
        return saveTodoDto;
    }

    @Override
    public TodoDto getTodo(long id) {
        Todo todo=todoRepository.findById(id).get();

        return modelMapper.map(todo,TodoDto.class);
    }
}
