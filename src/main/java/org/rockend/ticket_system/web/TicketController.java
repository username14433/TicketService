package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.TicketCreateDto;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.services.TicketService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String showListTickets(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();

        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    @GetMapping("/new")
    public String showNewTicketForm() {
        return "add-ticket-form";
    }

    @PostMapping("/new")
    public String createTicket(
            @Valid TicketCreateDto ticketDto,
            Authentication auth
    ) {
        ticketService.createTicket(ticketDto, auth);

        return "redirect:/tickets";
    }
}
