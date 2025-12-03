package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.*;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.services.AdminServiceImpl;
import org.rockend.ticket_system.services.TicketServiceImpl;
import org.rockend.ticket_system.services.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserServiceImpl userServiceImpl;
    private final AdminServiceImpl adminServiceImpl;
    private final TicketServiceImpl ticketServiceImpl;


    public AdminPanelController(UserServiceImpl userServiceImpl, AdminServiceImpl adminServiceImpl, TicketServiceImpl ticketServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.adminServiceImpl = adminServiceImpl;
        this.ticketServiceImpl = ticketServiceImpl;
    }

    @GetMapping
    public String adminPanelMain(Authentication auth, Model model) {
        User adminUser = ((CustomUserDetails)auth.getPrincipal()).getUser();
        AdminStatisticsDto adminStatisticsDto = adminServiceImpl.getAdminStatistics();
        model.addAttribute("admin", adminUser);
        model.addAttribute("adminStatistics", adminStatisticsDto);

        return "admin-panel-main";
    }

    @GetMapping("/users-list")
    public String adminPanelUsers(Model model) {
        List<User> users = userServiceImpl.getAllUsers();
        List<String> roles = Arrays.stream(UserRoles.values()).map(UserRoles::name).toList();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        return "admin-panel-users";
    }

    @PostMapping("/users-list")
    public String changeUserRole(ChangeUserRoleDto changeUserRoleDto) {
        adminServiceImpl.changeUserRole(changeUserRoleDto.id(), UserRoles.valueOf(changeUserRoleDto.newRole()));
        return "redirect:/admin/users-list";
    }

    @GetMapping("/tickets-list")
    public String adminPanelTickets(Model model) {
        List<Ticket> tickets = ticketServiceImpl.getAllTickets();

        model.addAttribute("tickets", tickets);
        return "admin-panel-tickets";
    }

    @GetMapping("/tickets-list/{id}")
    public String ticketDetails(@PathVariable("id") long id,
                                @RequestParam(value = "edit", required = false) Boolean editMode,
                                Model model) {
        Ticket ticket = ticketServiceImpl.getTicketById(id);
        model.addAttribute("ticket", ticket);

        editMode = editMode != null && editMode;
        model.addAttribute("editMode", editMode);

        List<User> executors =  userServiceImpl.getAllUsersByRole(UserRoles.EXECUTOR);
        model.addAttribute("executors", executors);
        return "admin-ticket-details";
    }

    @PostMapping("/tickets-list/{id}/change-status")
    public String changeTicketStatus(ChangeTicketStatusDto changeTicketStatusDto) {
        ticketServiceImpl.changeTicketStatus(changeTicketStatusDto.id(), changeTicketStatusDto.newStatus());
        return "redirect:/admin/tickets-list/" + changeTicketStatusDto.id();
    }

    @PostMapping("/tickets-list/{id}/delete")
    public String deleteTicket(@PathVariable("id") long id) {
        ticketServiceImpl.deleteTicket(id);
        return "redirect:/admin/tickets-list";
    }

    @PostMapping("/tickets-list/{id}/update")
    public String changeTicketInfo(@PathVariable("id") long id, ChangeTicketInfoDto changeTicketInfoDto) {
        ticketServiceImpl.changeTicketInfo(id, changeTicketInfoDto.newTitle(), changeTicketInfoDto.newDescription());
        return "redirect:/admin/tickets-list/" + id;
    }

    @PostMapping("/tickets-list/{id}/assign")
    public String assignTicket(@PathVariable("id") long id, AssignTicketDto assignTicketDto) {
        ticketServiceImpl.assignTicket(assignTicketDto);
        return "redirect:/admin/tickets-list/" + id;
    }

}
