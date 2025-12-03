package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.RegisterDto;

public interface RegistrationService {
    void registerUser(RegisterDto registerDto);
}
