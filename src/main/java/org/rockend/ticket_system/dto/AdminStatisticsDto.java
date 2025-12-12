package org.rockend.ticket_system.dto;

public class AdminStatisticsDto {
    private int usersCount;
    private int activeTicketsCount;
    private int doneTicketsCount;
    private int unassignedTicketsCount;

    public AdminStatisticsDto(int unassignedTicketsCount, int doneTicketsCount, int activeTicketsCount, int usersCount) {
        this.unassignedTicketsCount = unassignedTicketsCount;
        this.doneTicketsCount = doneTicketsCount;
        this.activeTicketsCount = activeTicketsCount;
        this.usersCount = usersCount;
    }

    public AdminStatisticsDto() { }

    public int getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public int getActiveTicketsCount() {
        return activeTicketsCount;
    }

    public void setActiveTicketsCount(int activeTicketsCount) {
        this.activeTicketsCount = activeTicketsCount;
    }

    public int getDoneTicketsCount() {
        return doneTicketsCount;
    }

    public void setDoneTicketsCount(int doneTicketsCount) {
        this.doneTicketsCount = doneTicketsCount;
    }

    public int getUnassignedTicketsCount() {
        return unassignedTicketsCount;
    }

    public void setUnassignedTicketsCount(int unassignedTicketsCount) {
        this.unassignedTicketsCount = unassignedTicketsCount;
    }
}
