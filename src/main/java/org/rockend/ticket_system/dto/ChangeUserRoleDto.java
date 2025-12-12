package org.rockend.ticket_system.dto;


public class ChangeUserRoleDto {
    private long id;
    private String newRole;

    public ChangeUserRoleDto(long id, String newRole) {
        this.id = id;
        this.newRole = newRole;
    }

    public ChangeUserRoleDto() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewRole() {
        return newRole;
    }

    public void setNewRole(String newRole) {
        this.newRole = newRole;
    }
}
