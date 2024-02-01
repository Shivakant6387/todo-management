package com.example.todo.management.security;

import com.example.todo.management.entity.User;
import com.example.todo.management.exception.ResourceNotFoundException;
import com.example.todo.management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String UserNameOrEmail) throws UsernameNotFoundException {
        User user=userRepository.findByUserNameOrEmail(UserNameOrEmail,UserNameOrEmail)
                .orElseThrow(()->new ResourceNotFoundException("User not exists by UserName or Email"));

        Set<GrantedAuthority>authorities=user.getRoles()
                .stream()
                .map((roles)->new SimpleGrantedAuthority(roles.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(UserNameOrEmail,user.getPassword(),authorities);
    }
}
