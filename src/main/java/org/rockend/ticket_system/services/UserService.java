package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.UserStatisticsDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(long id);
    List<User> getAllUsersByRole(UserRoles role);
    UserStatisticsDto getUserStatistics(Authentication auth);
}
