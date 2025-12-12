package org.rockend.ticket_system.dto;


import java.util.List;


public class UserTicketsDto {
    private List<TicketDto> createdTickets;
    private List<TicketDto> completedTickets;
    private List<TicketDto> assignedTickets;

    public UserTicketsDto() {
    }

    public UserTicketsDto(List<TicketDto> createdTickets,
                          List<TicketDto> completedTickets,
                          List<TicketDto> assignedTickets) {
        this.createdTickets = createdTickets;
        this.completedTickets = completedTickets;
        this.assignedTickets = assignedTickets;
    }


    public List<TicketDto> getCreatedTickets() {
        return createdTickets;
    }

    public void setCreatedTickets(List<TicketDto> createdTickets) {
        this.createdTickets = createdTickets;
    }

    public List<TicketDto> getCompletedTickets() {
        return completedTickets;
    }

    public void setCompletedTickets(List<TicketDto> completedTickets) {
        this.completedTickets = completedTickets;
    }

    public List<TicketDto> getAssignedTickets() {
        return assignedTickets;
    }

    public void setAssignedTickets(List<TicketDto> assignedTickets) {
        this.assignedTickets = assignedTickets;
    }
}
