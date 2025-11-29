package org.rockend.ticket_system.dto;

public record AdminStatisticsDto(int usersCount,
                                 int activeTicketsCount,
                                 int doneTicketsCount,
                                 int unassignedTicketsCount) { }
