package org.rockend.ticket_system.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.rockend.ticket_system.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT u.comments FROM User u WHERE u.username = :username")
    Comment findCommentByUsername(String username);

}
