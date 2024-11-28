package com.example.festquestbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.festquestbackend.models.quests.Quest;

public interface QuestRepository extends JPARepository<Quest, Long> {

}
