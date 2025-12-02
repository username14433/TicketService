package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.dto.UserStatisticsDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profileMainPage(Authentication auth, Model model) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        model.addAttribute("user", user);

        UserStatisticsDto userStatistics = userService.getUserStatistics(auth);
        model.addAttribute("userStatistics", userStatistics);
        return "user-profile";
    }
}
