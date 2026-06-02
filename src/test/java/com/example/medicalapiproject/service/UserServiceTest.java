package com.example.medicalapiproject.service;

import com.example.medicalapiproject.dto.UserCreateDTO;
import com.example.medicalapiproject.entity.Role;
import com.example.medicalapiproject.entity.User;
import com.example.medicalapiproject.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_ShouldCreateUserWithEncodedPasswordAndRole() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("doctor.ivanov");
        userCreateDTO.setPassword("password123");
        userCreateDTO.setEmail("doctor@example.com");

        when(userRepository.existsByUsername("doctor.ivanov")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded-password");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.createUser(userCreateDTO, Role.ROLE_DOCTOR);

        assertEquals("doctor.ivanov", user.getUsername());
        assertEquals("encoded-password", user.getPassword());
        assertEquals("doctor@example.com", user.getEmail());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    void createUser_ShouldThrowException_WhenUsernameAlreadyExists() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setUsername("patient.petrov");
        userCreateDTO.setPassword("password123");

        when(userRepository.existsByUsername("patient.petrov")).thenReturn(true);

        assertThrows(
                EntityExistsException.class,
                () -> userService.createUser(userCreateDTO, Role.ROLE_PATIENT)
        );
    }
}