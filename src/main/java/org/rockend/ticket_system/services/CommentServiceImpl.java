package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.CommentDto;
import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.dto.UserBasicDto;
import org.rockend.ticket_system.entity.Comment;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.repositories.CommentRepository;
import org.rockend.ticket_system.repositories.TicketRepository;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements  CommentService {

    private final CommentRepository commentRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              TicketRepository ticketRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(cacheNames = "comments", key = "#ticketId")
    public List<CommentDto> getAllCommentsByTicketId(long ticketId) {
        List<Comment> comments = commentRepository.findAllByTicketId(ticketId);
        return comments.stream()
                .map(CommentDto::fromEntity) // Используем статический метод
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(cacheNames = "comments", key = "#ticketId")
    public void addComment(long ticketId, String message, Authentication auth) {
        Comment comment = new Comment();

        UserBasicDto userDto = ((CustomUserDetails)auth.getPrincipal()).getUser();
        Ticket ticket = ticketRepository.findTicketById(ticketId);
        User user = userRepository.findUserById(userDto.getId());

        comment.setTicket(ticket);
        comment.setUser(user);
        comment.setMessage(message);

        commentRepository.save(comment);
    }

    @Override
    @CacheEvict(cacheNames = "comments", key = "#ticketId")
    public void deleteComment(long ticketId, long commentId) {
        commentRepository.deleteCommentById(commentId);
    }
}
