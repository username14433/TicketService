package org.rockend.ticket_system.entity.enums;

public enum UserRoles {
    USER(1),
    EXECUTOR(2),
    ADMIN(3);
    private int value;

    UserRoles(int value) {
        this.value = value;
    }
    public int getValue() {return value;}
}
