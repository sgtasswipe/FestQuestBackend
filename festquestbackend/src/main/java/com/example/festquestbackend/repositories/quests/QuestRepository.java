package com.example.festquestbackend.repositories.quests;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.festquestbackend.models.quests.Quest;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {



}