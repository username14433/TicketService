package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.AssignTicketDto;
import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.dto.TicketCreateDto;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.rockend.ticket_system.repositories.TicketRepository;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserService userService;

    public TicketService(TicketRepository ticketRepository, UserService userService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }


    public void createTicket(TicketCreateDto ticketDto, Authentication auth) {
        User creator = ((CustomUserDetails) auth.getPrincipal()).getUser();

        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDto.title());
        ticket.setDescription(ticketDto.description());
        ticket.setCreatedBy(creator);

        ticket.setAssignedTo(null);
        ticket.setStatus(StatusType.ACTIVE);


        ticketRepository.save(ticket);
    }

    public void changeTicketStatus(long id, StatusType newStatus) {
        ticketRepository.changeTicketStatus(id, newStatus, LocalDateTime.now());
    }

    public Ticket getTicketById(long id) {
        return ticketRepository.findTicketById(id);
    }

    public void deleteTicket(long id) {
        ticketRepository.deleteTicketById(id);
    }

    public void changeTicketInfo(long id, String newTitle, String newDescription) {
        ticketRepository.changeTicketInfo(id, newTitle, newDescription, LocalDateTime.now());
    }

    public void assignTicket(AssignTicketDto assignTicketDto) {
        User executor = userService.getUserById(assignTicketDto.executorId());
        ticketRepository.assignTicket(assignTicketDto.ticketId(), executor);
    }
}
