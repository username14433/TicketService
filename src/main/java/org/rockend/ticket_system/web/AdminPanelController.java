package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.AdminStatisticsDto;
import org.rockend.ticket_system.dto.ChangeTicketStatusDto;
import org.rockend.ticket_system.dto.ChangeUserRoleDto;
import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.services.AdminService;
import org.rockend.ticket_system.services.TicketService;
import org.rockend.ticket_system.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    private final UserService userService;
    private final AdminService adminService;
    private final TicketService ticketService;


    public AdminPanelController(UserService userService, AdminService adminService, TicketService ticketService) {
        this.userService = userService;
        this.adminService = adminService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String adminPanelMain(Authentication auth, Model model) {
        User adminUser = ((CustomUserDetails)auth.getPrincipal()).getUser();
        AdminStatisticsDto adminStatisticsDto = adminService.getAdminStatistics();
        model.addAttribute("admin", adminUser);
        model.addAttribute("adminStatistics", adminStatisticsDto);

        return "admin-panel-main";
    }

    @GetMapping("/users-list")
    public String adminPanelUsers(Model model) {
        List<User> users = userService.getAllUsers();
        List<String> roles = Arrays.stream(UserRoles.values()).map(UserRoles::name).toList();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        return "admin-panel-users";
    }

    @PostMapping("/users-list")
    public String changeUserRole(ChangeUserRoleDto changeUserRoleDto) {
        adminService.changeUserRole(changeUserRoleDto.id(), UserRoles.valueOf(changeUserRoleDto.newRole()));
        return "redirect:/admin/users-list";
    }

    @GetMapping("/tickets-list")
    public String adminPanelTickets(Model model) {
        List<Ticket> tickets = ticketService.getAllTickets();

        model.addAttribute("tickets", tickets);
        return "admin-panel-tickets";
    }

    @GetMapping("/tickets-list/{id}")
    public String ticketDetails(@PathVariable("id") long id, Model model) {
        Ticket ticket = ticketService.getTicketById(id);
        model.addAttribute("ticket", ticket);
        return "admin-ticket-details";
    }

    @PostMapping("/tickets-list/{id}/change-status")
    public String changeTicketStatus(ChangeTicketStatusDto changeTicketStatusDto) {
        ticketService.changeTicketStatus(changeTicketStatusDto.id(), changeTicketStatusDto.newStatus());
        return "redirect:/admin/tickets-list/" + changeTicketStatusDto.id();
    }

    @PostMapping("/tickets-list/{id}/delete")
    public String deleteTicket(@PathVariable("id") long id) {
        ticketService.deleteTicket(id);
        return "redirect:/admin/tickets-list";
    }

}
