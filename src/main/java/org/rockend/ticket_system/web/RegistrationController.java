package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.RegisterDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.services.RegistrationService;
import org.rockend.ticket_system.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerDto", new RegisterDto("", ""));
        return "register"; // Thymeleaf template: register.html
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerDto") RegisterDto dto, Model model) {

        try {
            registrationService.registerUser(dto);
        } catch (IllegalArgumentException ex) {
            // Например, если пользователь уже существует
            model.addAttribute("error", ex.getMessage());
            return "register";
        }

        // После успешной регистрации — на страницу логина
        return "redirect:/login";
    }


}
