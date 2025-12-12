package org.rockend.ticket_system.dto;

import org.rockend.ticket_system.entity.Comment;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

    public UserBasicDto toUserBasicDto(User user) {
        return UserBasicDto.fromEntity(user);
    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.fromEntity(comment);
    }

    public TicketDto toTicketDto(Ticket ticket) {
        return TicketDto.fromEntity(ticket);
    }
}