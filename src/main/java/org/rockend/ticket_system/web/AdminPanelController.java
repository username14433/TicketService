package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.ChangeUserRoleDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.UserRepository;
import org.rockend.ticket_system.services.AdminService;
import org.rockend.ticket_system.services.UserService;
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


    public AdminPanelController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
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


}
