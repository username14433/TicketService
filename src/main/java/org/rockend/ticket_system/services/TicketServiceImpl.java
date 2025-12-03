package org.rockend.ticket_system.services;

import org.modelmapper.ModelMapper;
import org.rockend.ticket_system.dto.AssignTicketDto;
import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.dto.TicketCreateDto;
import org.rockend.ticket_system.dto.UserTicketsDto;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.rockend.ticket_system.repositories.TicketRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements  TicketService {

    private final TicketRepository ticketRepository;
    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, UserServiceImpl userServiceImpl,
                             ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.userServiceImpl = userServiceImpl;
        this.modelMapper = modelMapper;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }


    public void createTicket(TicketCreateDto ticketDto, Authentication auth) {
        User creator = ((CustomUserDetails) auth.getPrincipal()).getUser();

        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
//        ticket.setTitle(ticketDto.title());
//        ticket.setDescription(ticketDto.description());
//        ticket.setCreatedBy(creator);

//        ticket.setAssignedTo(null);
//        ticket.setStatus(StatusType.ACTIVE);


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
        User executor = userServiceImpl.getUserById(assignTicketDto.executorId());
        ticketRepository.assignTicket(assignTicketDto.ticketId(), executor);
    }

    public UserTicketsDto getUserTickets(Authentication auth) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        List<Ticket> createdTickets = ticketRepository.findCreatedTicketsByUserId(user.getId());
        List<Ticket> completedTickets = ticketRepository.findCompletedTicketsByUserId(user.getId());
        List<Ticket> assignedTickets = ticketRepository.findAssignedTicketsByUserId(user.getId());

        return new UserTicketsDto(createdTickets, completedTickets, assignedTickets);
    }
}
