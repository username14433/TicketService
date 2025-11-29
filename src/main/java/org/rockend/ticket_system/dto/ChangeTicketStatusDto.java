package org.rockend.ticket_system.dto;

import org.rockend.ticket_system.entity.enums.StatusType;

public record ChangeTicketStatusDto(long id, StatusType newStatus) { }
