package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.CommentDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllCommentsByTicketId(long ticketId);
    void addComment(long ticketId, String message, Authentication auth);
    void deleteComment(long ticketId, long commentId);
}
