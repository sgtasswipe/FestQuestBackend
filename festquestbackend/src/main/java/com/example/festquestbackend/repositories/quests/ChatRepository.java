package com.example.festquestbackend.repositories.quests;

import com.example.festquestbackend.models.quests.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
