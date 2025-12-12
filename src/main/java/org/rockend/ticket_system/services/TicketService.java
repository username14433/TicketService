package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.AssignTicketDto;
import org.rockend.ticket_system.dto.TicketCreateDto;
import org.rockend.ticket_system.dto.TicketDto;
import org.rockend.ticket_system.dto.UserTicketsDto;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TicketService {
    List<TicketDto> getAllTickets();
    void createTicket(TicketCreateDto ticketDto, Authentication auth);
    void changeTicketStatus(long id, StatusType newStatus);
    TicketDto getTicketById(long id);
    void deleteTicket(long id);
    void changeTicketInfo(long id, String newTitle, String newDescription);
    void assignTicket(AssignTicketDto assignTicketDto);
    UserTicketsDto getUserTickets(Authentication auth);
}
