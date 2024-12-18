package com.example.festquestbackend.repositories.quests;

import org.springframework.stereotype.Repository;
import com.example.festquestbackend.models.quests.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {
    Optional<List<Duty>> findDutiesBySubQuestId(Long subQuestId);
    Optional<Duty> findByIdAndSubQuestId(long dutyId, long subQuestId);
}
