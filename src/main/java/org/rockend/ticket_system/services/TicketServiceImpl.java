package org.rockend.ticket_system.services;

import org.modelmapper.ModelMapper;
import org.rockend.ticket_system.dto.*;
import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.rockend.ticket_system.repositories.TicketRepository;
import org.rockend.ticket_system.repositories.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TicketServiceImpl implements  TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Cacheable(cacheNames = "tickets", key = "'all'")
    public List<TicketDto> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        return tickets.stream()
                .map(TicketDto::fromEntity)
                .collect(Collectors.toList());
    }


    @Override
    @CacheEvict(cacheNames = {"tickets", "userTickets", "userStatistics", "adminStatistics"}, allEntries = true)
    public void createTicket(TicketCreateDto ticketDto, Authentication auth) {
        UserBasicDto userDto = ((CustomUserDetails) auth.getPrincipal()).getUser();
        User creator = userRepository.findUserById(userDto.getId());

        Ticket ticket = new Ticket();
        ticket.setTitle(ticketDto.getTitle());
        ticket.setDescription(ticketDto.getDescription());
        ticket.setCreatedBy(creator);

        ticket.setAssignedTo(null);
        ticket.setStatus(StatusType.ACTIVE);


        ticketRepository.save(ticket);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "ticket", key = "#id"),     // один тикет
            @CacheEvict(cacheNames = "tickets", allEntries = true), // удаляем весь кэш
            @CacheEvict(cacheNames = "userTickets", allEntries = true),
            @CacheEvict(cacheNames = "userStatistics", allEntries = true),
            @CacheEvict(cacheNames = "adminStatistics", allEntries = true)
    })
    public void changeTicketStatus(long id, StatusType newStatus) {
        ticketRepository.changeTicketStatus(id, newStatus, LocalDateTime.now());
    }

    @Override
    @Cacheable(cacheNames = "ticket", key = "#id")
    public TicketDto getTicketById(long id) {
        Ticket ticket = ticketRepository.findTicketById(id);
        return TicketDto.fromEntity(ticket);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "ticket", key = "#id"),     // один тикет
            @CacheEvict(cacheNames = "tickets", allEntries = true), // удаляем весь кэш
            @CacheEvict(cacheNames = "userTickets", allEntries = true),
            @CacheEvict(cacheNames = "userStatistics", allEntries = true),
            @CacheEvict(cacheNames = "adminStatistics", allEntries = true)
    })
    public void deleteTicket(long id) {
        ticketRepository.deleteTicketById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "ticket", key = "#id"),     // один тикет
            @CacheEvict(cacheNames = "tickets", allEntries = true), // список ВСЕГДА full eviction
            @CacheEvict(cacheNames = "userTickets", allEntries = true),
    })
    public void changeTicketInfo(long id, String newTitle, String newDescription) {
        ticketRepository.changeTicketInfo(id, newTitle, newDescription, LocalDateTime.now());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "ticket", key = "#assignTicketDto.getTicketId()"),     // один тикет
            @CacheEvict(cacheNames = "tickets", allEntries = true), // список ВСЕГДА full eviction
            @CacheEvict(cacheNames = "userTickets", allEntries = true),
            @CacheEvict(cacheNames = "userStatistics", allEntries = true),
            @CacheEvict(cacheNames = "adminStatistics", allEntries = true)
    })
    public void assignTicket(AssignTicketDto assignTicketDto) {
        long executorId = assignTicketDto.getExecutorId();
        ticketRepository.assignTicket(assignTicketDto.getTicketId(), executorId);
    }

    @Override
    @Cacheable(cacheNames = "userTickets", key = "#auth.principal.user.id")
    public UserTicketsDto getUserTickets(Authentication auth) {
        UserBasicDto user = ((CustomUserDetails) auth.getPrincipal()).getUser();
        List<TicketDto> createdTickets = ticketRepository.findCreatedTicketsByUserId(user.getId())
                .stream().map(TicketDto::fromEntity).collect(Collectors.toList());
        List<TicketDto> completedTickets = ticketRepository.findCompletedTicketsByUserId(user.getId())
                .stream().map(TicketDto::fromEntity).filter(
                        ticket -> ticket.getStatus() == StatusType.DONE
                ).collect(Collectors.toList());
        List<TicketDto> assignedTickets = ticketRepository.findAssignedTicketsByUserId(user.getId())
                .stream().map(TicketDto::fromEntity).filter(
                        ticket -> ticket.getStatus() == StatusType.ACTIVE
                ).collect(Collectors.toList());

        return new UserTicketsDto(createdTickets, completedTickets, assignedTickets);
    }
}
