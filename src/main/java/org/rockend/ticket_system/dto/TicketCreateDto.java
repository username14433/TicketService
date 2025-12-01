package org.rockend.ticket_system.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record TicketCreateDto(
        @NotBlank(message = "Название тикета не может быть пустым")
        String title,
        @NotBlank(message = "Описание тикета не может быть пустым")
        String description
) { }
