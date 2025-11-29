package org.rockend.ticket_system.services;

import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void changeUserRole(int id, UserRoles newRole) {
        userRepository.changeUserRole(id, newRole);
    }
}
