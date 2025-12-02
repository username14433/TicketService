package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.ChangeTicketInfoDto;
import org.rockend.ticket_system.dto.TicketCreateDto;
import org.rockend.ticket_system.dto.UserTicketsDto;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.services.TicketService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/my")
    public String showMyTickets(@RequestParam("filter") String filter,
                                Authentication auth,
                                Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();
        System.out.println("tickets: " + tickets);
        UserTicketsDto userTickets = ticketService.getUserTickets(auth);
        List<Ticket> createdTickets = userTickets.createdTickets();
        List<Ticket> completedTickets = userTickets.completedTickets();

        model.addAttribute("tickets", tickets);
        model.addAttribute("filter", filter);
        model.addAttribute("createdTickets", createdTickets);
        model.addAttribute("completedTickets", completedTickets);
        return "user-tickets";
    }

    @GetMapping("/my/{id}")
    public String showTicketDetails(@PathVariable("id") long id,
                                    @RequestParam(value = "edit", required = false) Boolean editMode,
                                    Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);

        editMode = editMode != null && editMode;
        model.addAttribute("editMode", editMode);
        return "user-ticket-details";
    }

    @PostMapping("/my/{id}/update")
    public String changeTicketInfo(@PathVariable("id") long id, ChangeTicketInfoDto changeTicketInfoDto) {
        ticketService.changeTicketInfo(id, changeTicketInfoDto.newTitle(), changeTicketInfoDto.newDescription());
        return "redirect:/tickets/my/" + id;
    }


}
