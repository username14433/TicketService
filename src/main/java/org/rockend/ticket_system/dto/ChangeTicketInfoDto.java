package org.rockend.ticket_system.dto;

public class ChangeTicketInfoDto {
    private String newTitle;
    private String newDescription;

    public ChangeTicketInfoDto(String newTitle, String newDescription) {
        this.newTitle = newTitle;
        this.newDescription = newDescription;
    }

    public ChangeTicketInfoDto() { }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
}
