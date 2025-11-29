package org.rockend.ticket_system.repositories;

import org.rockend.ticket_system.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findTicketById(Integer id);

    @Query("SELECT t FROM Ticket t JOIN User u ON t.createdBy.id = u.id WHERE u.username = :username")
    List<Ticket> findAssignedTicketsByUsername(String username);

    @Query("SELECT t FROM Ticket t JOIN User u ON t.assignedTo.id = u.id WHERE u.username = :username")
    List<Ticket> findCreatedTicketsByUsername(String username);

    void deleteTicketByTitle(String title);
}
