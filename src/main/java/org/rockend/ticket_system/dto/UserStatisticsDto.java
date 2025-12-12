package org.rockend.ticket_system.dto;

public class UserStatisticsDto {
    private int createdTicketsCount;
    private int completedTicketsCount;
    private int assignedTicketsCount;
    //int placeInBestExecutorsRating


    public UserStatisticsDto(int createdTicketsCount, int completedTicketsCount, int assignedTicketsCount) {
        this.createdTicketsCount = createdTicketsCount;
        this.completedTicketsCount = completedTicketsCount;
        this.assignedTicketsCount = assignedTicketsCount;
    }

    public UserStatisticsDto() { }

    public int getCreatedTicketsCount() {
        return createdTicketsCount;
    }

    public void setCreatedTicketsCount(int createdTicketsCount) {
        this.createdTicketsCount = createdTicketsCount;
    }

    public int getCompletedTicketsCount() {
        return completedTicketsCount;
    }

    public void setCompletedTicketsCount(int completedTicketsCount) {
        this.completedTicketsCount = completedTicketsCount;
    }

    public int getAssignedTicketsCount() {
        return assignedTicketsCount;
    }

    public void setAssignedTicketsCount(int assignedTicketsCount) {
        this.assignedTicketsCount = assignedTicketsCount;
    }
}