package org.rockend.ticket_system.web;

import jakarta.validation.Valid;
import org.rockend.ticket_system.dto.RegisterDto;
import org.rockend.ticket_system.services.RegistrationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final RegistrationServiceImpl registrationServiceImpl;

    public RegistrationController(RegistrationServiceImpl registrationServiceImpl) {
        this.registrationServiceImpl = registrationServiceImpl;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Thymeleaf template: register.html
    }

    @PostMapping("/register")
    public String register(@Valid RegisterDto dto, Model model) {

        try {
            registrationServiceImpl.registerUser(dto);
        } catch (IllegalArgumentException ex) {
            // Например, если пользователь уже существует
            model.addAttribute("error", ex.getMessage());
            return "register";
        }

        // После успешной регистрации — на страницу логина
        return "redirect:/login";
    }


}
