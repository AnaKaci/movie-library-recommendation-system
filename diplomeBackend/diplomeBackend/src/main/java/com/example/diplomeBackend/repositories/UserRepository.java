package com.example.diplomeBackend.repositories;
import com.example.diplomeBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%'))" +
            "OR LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchUser(@Param("query") String query);
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.userId = :id")
    User findUserById(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE LOWER(REPLACE(u.email, ' ', '')) = LOWER(REPLACE(:email, ' ', ''))")
    Optional<User> findByEmail(@Param("email") String email);
}