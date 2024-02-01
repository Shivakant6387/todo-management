package com.example.todo.management.service;

import com.example.todo.management.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
}
