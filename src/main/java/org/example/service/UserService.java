package org.example.service;

import lombok.AllArgsConstructor;
import org.example.dto.response.UserDTO;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getCurrentUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    private UserDTO mapToDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .userName(user.getName())
                .userEmail(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

}
