package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.AdminStatisticsDto;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.TicketRepository;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;
    private final TicketServiceImpl ticketServiceImpl;
    private final TicketRepository ticketRepository;

    public AdminServiceImpl(UserRepository userRepository, UserServiceImpl userServiceImpl, TicketServiceImpl ticketServiceImpl, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.userServiceImpl = userServiceImpl;
        this.ticketServiceImpl = ticketServiceImpl;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void changeUserRole(long id, UserRoles newRole) {
        userRepository.changeUserRole(id, newRole);
    }

    @Override
    @Cacheable(cacheNames = "adminStatistics")
    public AdminStatisticsDto getAdminStatistics() {
        int usersCount = userServiceImpl.getAllUsers().size();
        int activeTicketsCount = ticketRepository.findAll().stream().filter(
                ticket -> ticket.getStatus() == StatusType.ACTIVE).toList().size();
        int doneTicketsCount = ticketRepository.findAll().stream().filter(
                ticket -> ticket.getStatus() == StatusType.DONE).toList().size();
        int unassignedTicketsCount = ticketRepository.findAll().stream().filter(
                ticket -> ticket.getAssignedTo() == null).toList().size();

        AdminStatisticsDto adminStatisticsDto = new AdminStatisticsDto(
                unassignedTicketsCount, doneTicketsCount, activeTicketsCount, usersCount
        );
        return adminStatisticsDto;
    }
}
