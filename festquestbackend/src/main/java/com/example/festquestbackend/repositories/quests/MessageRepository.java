package com.example.festquestbackend.repositories.quests;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.festquestbackend.models.quests.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {



}
