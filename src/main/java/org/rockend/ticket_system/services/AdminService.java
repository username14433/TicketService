package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.AdminStatisticsDto;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final TicketService ticketService;

    public AdminService(UserRepository userRepository, UserService userService, TicketService ticketService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Transactional
    public void changeUserRole(long id, UserRoles newRole) {
        userRepository.changeUserRole(id, newRole);
    }

    public AdminStatisticsDto getAdminStatistics() {
        int usersCount = userService.getAllUsers().size();
        int activeTicketsCount = ticketService.getAllTickets().stream().filter(
                ticket -> ticket.getStatus() == StatusType.ACTIVE).toList().size();
        int doneTicketsCount = ticketService.getAllTickets().stream().filter(
                ticket -> ticket.getStatus() == StatusType.DONE).toList().size();
        int unassignedTicketsCount = ticketService.getAllTickets().stream().filter(
                ticket -> ticket.getAssignedTo() == null).toList().size();

        AdminStatisticsDto adminStatisticsDto = new AdminStatisticsDto(
                usersCount, activeTicketsCount, doneTicketsCount, unassignedTicketsCount
        );
        return adminStatisticsDto;
    }
}
