package com.example.festquestbackend.repositories.users;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.festquestbackend.models.users.QuestParticipant;

@Repository
public interface QuestParticipantRepository extends JpaRepository<QuestParticipant, Long> {



}
