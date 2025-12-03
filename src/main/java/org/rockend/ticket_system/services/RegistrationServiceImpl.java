package org.rockend.ticket_system.services;

import org.modelmapper.ModelMapper;
import org.rockend.ticket_system.dto.RegisterDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public RegistrationServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public void registerUser(RegisterDto registerDto) {
//        if (userRepository.findByUsername(registerDto.username()) != null) {
//            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
//        }

        User user = modelMapper.map(registerDto, User.class);
        user.setPasswordHash(passwordEncoder.encode(registerDto.password()));


        user.setRole(UserRoles.USER);


        userRepository.save(user);
    }
}
