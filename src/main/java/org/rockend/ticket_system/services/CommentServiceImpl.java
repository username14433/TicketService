package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.entity.Comment;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.repositories.CommentRepository;
import org.rockend.ticket_system.repositories.TicketRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements  CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;

    public CommentServiceImpl(CommentRepository commentRepository, TicketRepository ticketRepository) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
    }

    public List<Comment> getAllCommentsByTicketId(long ticketId) {
        return commentRepository.findAllByTicketId(ticketId);
    }

    public void addComment(long ticketId, String message, Authentication auth) {
        Comment comment = new Comment();

        User user = ((CustomUserDetails)auth.getPrincipal()).getUser();
        Ticket ticket = ticketRepository.findTicketById(ticketId);

        comment.setTicket(ticket);
        comment.setUser(user);
        comment.setMessage(message);

        commentRepository.save(comment);
    }

    public void deleteComment(long commentId) {
        commentRepository.deleteCommentById(commentId);
    }
}
