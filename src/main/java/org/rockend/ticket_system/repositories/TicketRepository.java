package org.rockend.ticket_system.repositories;

import org.rockend.ticket_system.entity.Ticket;
import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findTicketById(long id);

    void deleteTicketByTitle(String title);
    void deleteTicketById(long id);

    @Modifying
    @Query("UPDATE Ticket t SET t.status = :newStatus, t.updatedAt = :updatedAt WHERE t.id = :id")
    void changeTicketStatus(@Param("id") long id,
                            @Param("newStatus") StatusType newStatus,
                            @Param("updatedAt") LocalDateTime updatedAt);

    @Modifying
    @Query("UPDATE Ticket t" +
            " SET t.title = :newTitle, t.description = :newDescription, t.updatedAt = :updatedAt" +
            " WHERE t.id = :id")
    void changeTicketInfo(@Param("id") long id,
                          @Param("newTitle") String newTitle,
                          @Param("newDescription") String newDescription,
                          @Param("updatedAt") LocalDateTime updatedAt);

    @Modifying
    @Query("UPDATE Ticket t SET t.assignedTo = :executor WHERE t.id = :ticketId")
    void assignTicket(@Param("ticketId") long ticketId, @Param("executor") User executor);

    @Query("SELECT t FROM Ticket t JOIN User u ON t.assignedTo.id = u.id WHERE u.id = :id")
    List<Ticket> findAssignedTicketsByUserId(@Param("id") long id);

    @Query("SELECT t FROM Ticket t JOIN User u ON t.createdBy.id = u.id WHERE u.id = :id")
    List<Ticket> findCreatedTicketsByUserId(@Param("id") long id);

    @Query("SELECT t FROM Ticket t JOIN User u ON t.assignedTo.id = u.id WHERE u.id = :id AND t.status = 'DONE'")
    List<Ticket> findCompletedTicketsByUserId(@Param("id") long id);
}
