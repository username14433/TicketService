package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.AdminStatisticsDto;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;
    private final TicketServiceImpl ticketServiceImpl;

    public AdminServiceImpl(UserRepository userRepository, UserServiceImpl userServiceImpl, TicketServiceImpl ticketServiceImpl) {
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
        this.ticketServiceImpl = ticketServiceImpl;
    }

    public void changeUserRole(long id, UserRoles newRole) {
        userRepository.changeUserRole(id, newRole);
    }

    public AdminStatisticsDto getAdminStatistics() {
        int usersCount = userServiceImpl.getAllUsers().size();
        int activeTicketsCount = ticketServiceImpl.getAllTickets().stream().filter(
                ticket -> ticket.getStatus() == StatusType.ACTIVE).toList().size();
        int doneTicketsCount = ticketServiceImpl.getAllTickets().stream().filter(
                ticket -> ticket.getStatus() == StatusType.DONE).toList().size();
        int unassignedTicketsCount = ticketServiceImpl.getAllTickets().stream().filter(
                ticket -> ticket.getAssignedTo() == null).toList().size();

        AdminStatisticsDto adminStatisticsDto = new AdminStatisticsDto(
                usersCount, activeTicketsCount, doneTicketsCount, unassignedTicketsCount
        );
        return adminStatisticsDto;
    }
}
