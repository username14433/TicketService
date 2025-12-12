package org.rockend.ticket_system.dto;

import org.rockend.ticket_system.entity.enums.StatusType;

public class ChangeTicketStatusDto {
    private long id;
    private StatusType newStatus;

    public ChangeTicketStatusDto(long id, StatusType newStatus) {
        this.id = id;
        this.newStatus = newStatus;
    }

    public ChangeTicketStatusDto() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StatusType getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(StatusType newStatus) {
        this.newStatus = newStatus;
    }
}
