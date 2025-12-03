package org.rockend.ticket_system.services;
import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.dto.UserStatisticsDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.TicketRepository;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TicketRepository ticketRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.ticketRepository = ticketRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public User getUserById(long id) {
        return userRepository.findUserById(id);
    }

    public List<User> getAllUsersByRole(UserRoles role) {
        return userRepository.findAllByRole(role);
    }

    public UserStatisticsDto getUserStatistics(Authentication auth) {
        User user = ((CustomUserDetails) auth.getPrincipal()).getUser();

        int createdTicketsCount = ticketRepository.findCreatedTicketsByUserId(user.getId()).size();
        int completedTicketsCount = ticketRepository.findCompletedTicketsByUserId(user.getId()).size();
        int assignedTicketsCount = ticketRepository.findAssignedTicketsByUserId(user.getId()).size();
        //int placeInBestExecutorsRating
        return new UserStatisticsDto(createdTicketsCount, completedTicketsCount, assignedTicketsCount);
    }

}
