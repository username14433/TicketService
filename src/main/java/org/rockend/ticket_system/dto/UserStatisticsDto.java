package org.rockend.ticket_system.dto;

public record UserStatisticsDto(int createdTicketsCount,
                                int completedTicketsCount,
                                int assignedTicketsCount
                                //int placeInBestExecutorsRating
) { }
