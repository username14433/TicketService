package org.rockend.ticket_system.dto;

import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.enums.StatusType;
import java.time.LocalDateTime;

public class TicketDto {
    private long id;
    private String title;
    private String description;
    private StatusType status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserBasicDto createdBy;
    private UserBasicDto assignedTo;

    // Конструктор со всеми полями
    public TicketDto(
            long id,
            String title,
            String description,
            StatusType status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            UserBasicDto createdBy,
            UserBasicDto assignedTo
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
    }


    // Статический метод fromEntity
    public static TicketDto fromEntity(Ticket ticket) {
        if (ticket == null) return null;
        return new TicketDto(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                UserBasicDto.fromEntity(ticket.getCreatedBy()),
                UserBasicDto.fromEntity(ticket.getAssignedTo())
        );
    }

    public TicketDto() {
    }

    // Геттеры
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public StatusType getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserBasicDto getCreatedBy() {
        return createdBy;
    }

    public UserBasicDto getAssignedTo() {
        return assignedTo;
    }

    // Сеттеры
    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedBy(UserBasicDto createdBy) {
        this.createdBy = createdBy;
    }

    public void setAssignedTo(UserBasicDto assignedTo) {
        this.assignedTo = assignedTo;
    }


}