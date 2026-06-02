package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.UserCreateDTO;
import com.example.medicalapiproject.entity.Role;
import com.example.medicalapiproject.entity.User;
import com.example.medicalapiproject.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(UserCreateDTO userCreateDTO, Role role) {
        if (userRepository.existsByUsername(userCreateDTO.getUsername())) {
            throw new EntityExistsException(
                    "User already exists with username: " + userCreateDTO.getUsername()
            );
        }

        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }
}
