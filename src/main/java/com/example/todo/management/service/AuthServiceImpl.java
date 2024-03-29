package com.example.todo.management.service;

import com.example.todo.management.dto.JwtAuthResponse;
import com.example.todo.management.dto.LoginDto;
import com.example.todo.management.dto.RegisterDto;
import com.example.todo.management.entity.Role;
import com.example.todo.management.entity.User;
import com.example.todo.management.exception.TodoAPIException;
import com.example.todo.management.repository.RoleRepository;
import com.example.todo.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {
        //check user name already exists in database
        if (userRepository.existsByUserName(registerDto.getUsername())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "User name already exists!");
        }
        //check email is already exists in database
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email is already exists!");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setUserName(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User Register Successfully!.";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserNameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        String role = null;
        Optional<User> userOptional = userRepository.findByUserNameOrEmail(loginDto.getUserNameOrEmail(), loginDto.getUserNameOrEmail());
        if (userOptional.isPresent()) {
            User loginUser = userOptional.get();
            Optional<Role> optionalRole = loginUser.getRoles().stream().findFirst();
            if (optionalRole.isPresent()) {
                Role userRole = optionalRole.get();
                role = userRole.getName();
            }
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }
}
