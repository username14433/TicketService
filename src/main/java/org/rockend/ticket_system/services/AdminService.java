package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.AdminStatisticsDto;
import org.rockend.ticket_system.entity.enums.UserRoles;

public interface AdminService {
    void changeUserRole(long id, UserRoles newRole);
    AdminStatisticsDto getAdminStatistics();

}
