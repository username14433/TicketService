package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.LoginUserDto;
import org.rockend.ticket_system.dto.RegisterDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.username()) != null) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        User user = new User();
        user.setUsername(registerDto.username());
        user.setPasswordHash(passwordEncoder.encode(registerDto.password()));


        user.setRole(UserRoles.USER);


        userRepository.save(user);
    }
}
