package org.rockend.ticket_system.services;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public User getUserById(long id) {
        return userRepository.findUserById(id);
    }

    public List<User> getAllUsersByRole(UserRoles role) {
        return userRepository.findAllByRole(role);
    }

}
