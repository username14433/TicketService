package org.rockend.ticket_system.services;

import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.dto.UserBasicDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        UserBasicDto userDto = UserBasicDto.fromEntity(user);
        return new CustomUserDetails(userDto, user.getPasswordHash());
    }
}
