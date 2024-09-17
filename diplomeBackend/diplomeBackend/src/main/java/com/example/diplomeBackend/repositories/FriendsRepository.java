package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Friends;
import com.example.diplomeBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {
    @Query("SELECT f.friends FROM Friends f WHERE f.user = :user")
    List<User> getAllUserFriends(@Param("user") User user);

    Optional<Friends> findByUser(User user);

}
