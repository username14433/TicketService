package org.rockend.ticket_system.dto;

import java.time.LocalDateTime;

public record TicketCreateDto(
        String title,
        String description
) { }
