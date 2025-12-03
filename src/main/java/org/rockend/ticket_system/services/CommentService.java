package org.rockend.ticket_system.services;

import org.rockend.ticket_system.entity.Comment;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {
    List<Comment> getAllCommentsByTicketId(long ticketId);
    void addComment(long ticketId, String message, Authentication auth);
}
