package org.rockend.ticket_system.dto;

import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.entity.User;

public class UserBasicDto {
    private long id;
    private String username;
    private UserRoles role;

    public UserBasicDto(
            long id,
            String username,
            UserRoles role
    ) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public UserBasicDto() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public static UserBasicDto fromEntity(User user) {
        if (user == null) return null;
        return new UserBasicDto(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}