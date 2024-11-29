package com.example.festquestbackend.repositories.quests;

import org.springframework.stereotype.Repository;
import com.example.festquestbackend.models.quests.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {



}
