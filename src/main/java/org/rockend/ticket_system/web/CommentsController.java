package org.rockend.ticket_system.web;

import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.entity.Comment;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.services.CommentService;
import org.rockend.ticket_system.services.CommentServiceImpl;
import org.rockend.ticket_system.services.TicketServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CommentsController {

    private final CommentServiceImpl commentServiceImpl;
    private final TicketServiceImpl ticketServiceImpl;

    public CommentsController(CommentServiceImpl commentServiceImpl, TicketServiceImpl ticketServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
        this.ticketServiceImpl = ticketServiceImpl;
    }

    @GetMapping("/tickets/{id}/comments")
    public String showCommentsPage(@PathVariable("id") long id, Authentication auth, Model model) {
        List<Comment> comments = commentServiceImpl.getAllCommentsByTicketId(id);
        model.addAttribute("comments", comments);

        Ticket ticket = ticketServiceImpl.getTicketById(id);
        model.addAttribute("ticket", ticket);
        System.out.println("Ticket: " + ticket);

        User currentUser = ((CustomUserDetails)auth.getPrincipal()).getUser();
        model.addAttribute("currentUser", currentUser);
        return "comments";
    }

    @PostMapping("/tickets/{id}/comments/add")
    public String addComment(@PathVariable("id") long id, String message, Authentication auth) {
        commentServiceImpl.addComment(id, message, auth);
        return  "redirect:/tickets/" + id + "/comments";
    }

    @PostMapping("/tickets/{ticketId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("ticketId") long ticketId,  @PathVariable("commentId") long commentId) {
        commentServiceImpl.deleteComment(commentId);
        return "redirect:/tickets/" + ticketId + "/comments";
    }
}
