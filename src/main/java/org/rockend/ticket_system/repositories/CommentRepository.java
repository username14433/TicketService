package org.rockend.ticket_system.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.rockend.ticket_system.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByTicketId(long ticketId);

    void deleteCommentById(long id);
}
