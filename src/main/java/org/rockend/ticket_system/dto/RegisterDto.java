package org.rockend.ticket_system.dto;


import jakarta.validation.constraints.NotBlank;

public class RegisterDto {
    @NotBlank(message = "Некорректное имя пользователя")
    private String username;
    @NotBlank(message = "Некорректный пароль")
    private String password;

    public RegisterDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RegisterDto() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

