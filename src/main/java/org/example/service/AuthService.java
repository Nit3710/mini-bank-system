package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.request.AuthRequest;
import org.example.dto.response.AuthResponse;
import org.example.entity.enums.Role;
import org.example.entity.User;
import org.example.exception.UnauthorizedAccessException;
import org.example.repository.UserRepository;
import org.example.security.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(AuthRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setName(request.getUserName());

        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(
                new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))));

        return new AuthResponse(token, "USER");
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedAccessException("Invalid email or password");
        }

        String token = jwtTokenProvider.generateToken(
                new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))));

        return new AuthResponse(token, user.getRole().name());
    }
}
