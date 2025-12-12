package org.rockend.ticket_system.web;

import jakarta.validation.Valid;
import org.rockend.ticket_system.dto.RegisterDto;
import org.rockend.ticket_system.exceptions.UserAlreadyExists;
import org.rockend.ticket_system.services.RegistrationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final RegistrationServiceImpl registrationServiceImpl;

    public RegistrationController(RegistrationServiceImpl registrationServiceImpl) {
        this.registrationServiceImpl = registrationServiceImpl;
    }

    @GetMapping("/register")
    public String showRegistrationForm(RegisterDto registerDto,  Model model) {
        model.addAttribute("registerDto", registerDto);
        return "register"; // Thymeleaf template: register.html
    }

    @PostMapping("/register")
    public String register(@Valid RegisterDto registerDto,
                           BindingResult bindingResult,
                           Model model) {


        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            registrationServiceImpl.registerUser(registerDto);
        } catch (UserAlreadyExists ex) {
            model.addAttribute("errorMsg", ex.getMessage());
            return "register";
        }
        return "redirect:/login";
    }


}
