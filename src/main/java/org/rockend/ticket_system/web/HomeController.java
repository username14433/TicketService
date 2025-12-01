package org.rockend.ticket_system.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {


    @GetMapping("/")
    public String homePage() {
        return "index";
    }

//    @GetMapping("/createTicket")
//    public String createTicketForm(Model model) {
//
//    }


}
