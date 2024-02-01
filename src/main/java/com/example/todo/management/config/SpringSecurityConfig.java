package com.example.todo.management.config;

import com.example.todo.management.security.JwtAuthenticationEntryPoint;
import com.example.todo.management.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests((authorization) -> {
//            authorization.requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN");
//            authorization.requestMatchers(HttpMethod.PUT,"/api/**").hasRole("ADMIN");
//            authorization.requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN");
//            authorization.requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN","USER");
//            authorization.requestMatchers(HttpMethod.PATCH,"/api/**").hasAnyRole("ADMIN","USER");
//            authorization.requestMatchers(HttpMethod.GET).permitAll();
            authorization.requestMatchers("/api/auth/**").permitAll();
            authorization.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails shiva = User.builder()
//                .username("shiva")
//                .password(passwordEncoder().encode("Indian@#2024%$#"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("Indian@#2024%$#"))
//                .roles("ADMIN")
//                .build();
//        UserDetails manager = User.builder()
//                .username("manager")
//                .password(passwordEncoder().encode("Indian@#2024%$#"))
//                .roles("MANAGER")
//                .build();
//        return new InMemoryUserDetailsManager(shiva, admin, manager);
//    }
}
