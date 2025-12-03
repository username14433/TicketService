package org.rockend.ticket_system.dto;

import org.rockend.ticket_system.entity.Ticket;

import java.util.List;

public record UserTicketsDto(List<Ticket> createdTickets,
                             List<Ticket> completedTickets,
                             List<Ticket> assignedTickets) { }
