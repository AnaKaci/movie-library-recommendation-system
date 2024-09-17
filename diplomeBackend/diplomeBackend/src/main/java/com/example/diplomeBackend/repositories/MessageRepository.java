package com.example.diplomeBackend.repositories;

import com.example.diplomeBackend.models.Message;
import com.example.diplomeBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m from Message m WHERE m.sender = :sender AND m.receiver = :receiver")
    List<Message> findBySenderAndReceiver(@Param("sender") User sender, @Param("receiver") User receiver);
}
