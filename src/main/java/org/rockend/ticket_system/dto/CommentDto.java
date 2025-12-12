package org.rockend.ticket_system.dto;


import java.time.LocalDateTime;

public class CommentDto {
    private int id;
    private String message;
    private LocalDateTime createdAt;
    private UserBasicDto user;
    private Long ticketId;

    public CommentDto(
            int id,
            String message,
            LocalDateTime createdAt,
            UserBasicDto user,
            Long ticketId
    ) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.user = user;
        this.ticketId = ticketId;
    }

    public CommentDto() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserBasicDto getUser() {
        return user;
    }

    public void setUser(UserBasicDto user) {
        this.user = user;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public static CommentDto fromEntity(org.rockend.ticket_system.entity.Comment comment) {
        if (comment == null) return null;
        return new CommentDto(
                comment.getId(),
                comment.getMessage(),
                comment.getCreatedAt(),
                UserBasicDto.fromEntity(comment.getUser()),
                (long) comment.getTicket().getId()
        );
    }
}