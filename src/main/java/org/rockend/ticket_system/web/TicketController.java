package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.*;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.services.TicketServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketServiceImpl ticketServiceImpl;

    public TicketController(TicketServiceImpl ticketServiceImpl) {
        this.ticketServiceImpl = ticketServiceImpl;
    }

    @GetMapping
    public String showListTickets(Authentication auth, Model model) {
        List<Ticket> tickets = ticketServiceImpl.getAllTickets();

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
        ticketServiceImpl.createTicket(ticketDto, auth);

        return "redirect:/tickets";
    }

    @GetMapping("/my")
    public String showMyTickets(@RequestParam("filter") String filter,
                                Authentication auth,
                                Model model) {
        List<Ticket> tickets = ticketServiceImpl.getAllTickets();
        System.out.println("tickets: " + tickets);
        UserTicketsDto userTickets = ticketServiceImpl.getUserTickets(auth);
        List<Ticket> createdTickets = userTickets.createdTickets();
        List<Ticket> completedTickets = userTickets.completedTickets();
        List<Ticket> assignedTickets = userTickets.assignedTickets();
        System.out.println("assignedTickets: " + assignedTickets);

        model.addAttribute("tickets", tickets);
        model.addAttribute("filter", filter);
        model.addAttribute("createdTickets", createdTickets);
        model.addAttribute("completedTickets", completedTickets);
        model.addAttribute("assignedTickets", assignedTickets);
        return "user-tickets";
    }

    @GetMapping("/my/{id}")
    public String showTicketDetails(@PathVariable("id") long id,
                                    @RequestParam(value = "edit", required = false) Boolean editMode,
                                    Authentication auth,
                                    Model model) {
        Ticket ticket = ticketServiceImpl.getTicketById(id);
        model.addAttribute("ticket", ticket);

        editMode = editMode != null && editMode;
        model.addAttribute("editMode", editMode);

        User currentUser = ((CustomUserDetails)auth.getPrincipal()).getUser();
        model.addAttribute("currentUser", currentUser);
        return "user-ticket-details";
    }

    @PostMapping("/my/{id}/update")
    public String changeTicketInfo(@PathVariable("id") long id, ChangeTicketInfoDto changeTicketInfoDto) {
        ticketServiceImpl.changeTicketInfo(id, changeTicketInfoDto.newTitle(), changeTicketInfoDto.newDescription());
        return "redirect:/tickets/my/" + id;
    }

    @PostMapping("/my/{id}/change-status")
    public String changeTicketStatus(ChangeTicketStatusDto changeTicketStatusDto) {
        ticketServiceImpl.changeTicketStatus(changeTicketStatusDto.id(), changeTicketStatusDto.newStatus());
        return "redirect:/tickets/my/" + changeTicketStatusDto.id();
    }


}
