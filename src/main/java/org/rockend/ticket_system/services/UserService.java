package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.UserBasicDto;
import org.rockend.ticket_system.dto.UserStatisticsDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    List<UserBasicDto> getAllUsers();
    UserBasicDto getUserById(long id);
    List<UserBasicDto> getAllUsersByRole(UserRoles role);
    UserStatisticsDto getUserStatistics(Authentication auth);
}
