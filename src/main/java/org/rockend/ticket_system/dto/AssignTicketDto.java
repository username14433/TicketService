package org.rockend.ticket_system.dto;

public class AssignTicketDto {
    private long ticketId;
    private long executorId;

    public AssignTicketDto(long ticketId, long executorId) {
        this.ticketId = ticketId;
        this.executorId = executorId;
    }

    public AssignTicketDto() { }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(long executorId) {
        this.executorId = executorId;
    }
}
