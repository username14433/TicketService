package org.rockend.ticket_system.services;
import org.rockend.ticket_system.dto.CustomUserDetails;
import org.rockend.ticket_system.dto.UserBasicDto;
import org.rockend.ticket_system.dto.UserStatisticsDto;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.rockend.ticket_system.repositories.TicketRepository;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Cacheable(cacheNames = "users", key = "'all'")
    public List<UserBasicDto> getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return users.stream()
                .map(UserBasicDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "users", key = "#id")
    public UserBasicDto getUserById(long id) {
        User user = userRepository.findUserById(id);
        return UserBasicDto.fromEntity(user);
    }

    @Override
//    @Cacheable(cacheNames = "allUsersByRole")
    public List<UserBasicDto> getAllUsersByRole(UserRoles role) {
        List<User> users = userRepository.findAllByRole(role);
        return users.stream()
                .map(UserBasicDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(cacheNames = "userStatistics", key = "#auth.principal.user.id")
    public UserStatisticsDto getUserStatistics(Authentication auth) {
        UserBasicDto user = ((CustomUserDetails) auth.getPrincipal()).getUser();

        int createdTicketsCount = ticketRepository.findCreatedTicketsByUserId(user.getId()).size();
        int completedTicketsCount = ticketRepository.findCompletedTicketsByUserId(user.getId()).size();
        int assignedTicketsCount = ticketRepository.findAssignedTicketsByUserId(user.getId()).stream().filter(
                ticket -> ticket.getStatus() == StatusType.ACTIVE
        ).toList().size();
        //int placeInBestExecutorsRating
        return new UserStatisticsDto(createdTicketsCount, completedTicketsCount, assignedTicketsCount);
    }

}
