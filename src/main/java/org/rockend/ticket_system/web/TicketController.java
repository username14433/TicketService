package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.dto.TicketCreateDto;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.services.TicketService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showNewTicketForm(Model model) {
        model.addAttribute("ticketDto", new TicketCreateDto("", ""));
        return "addTicketForm";
    }

    @PostMapping("/new")
    public String createTicket(
            @ModelAttribute("ticketDto") TicketCreateDto ticketDto,
            Authentication auth,
            Model model
    ) {
        ticketService.createTicket(ticketDto, auth);

        return "redirect:/tickets";
    }
}
