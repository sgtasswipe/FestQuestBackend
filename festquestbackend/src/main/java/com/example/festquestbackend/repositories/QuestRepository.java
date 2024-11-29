package com.example.festquestbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.festquestbackend.models.quests.Quest;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    Optional<Quest> findQuestsById(Long id);
// JpaRepository eller CrudRepository??


}
