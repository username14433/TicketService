package org.rockend.ticket_system.repositories;

import org.rockend.ticket_system.entity.User;
import org.rockend.ticket_system.entity.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserById(Integer id);
    Optional<User> findUserByUsername(String username);

    void deleteUserByUsername(String username);

    User findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.role = :newRole WHERE u.id = :id")
    void changeUserRole(@Param("id") int id, @Param("newRole") UserRoles newRole);
}
