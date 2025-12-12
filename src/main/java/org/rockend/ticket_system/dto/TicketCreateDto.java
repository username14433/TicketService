package org.rockend.ticket_system.dto;

import jakarta.validation.constraints.NotBlank;


public class TicketCreateDto{
    @NotBlank(message = "Название тикета не может быть пустым")
    private String title;
    @NotBlank(message = "Описание тикета не может быть пустым")
    private String description;


    public TicketCreateDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public TicketCreateDto() { }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
