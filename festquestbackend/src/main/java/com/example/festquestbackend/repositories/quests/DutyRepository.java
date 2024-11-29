package com.example.festquestbackend.repositories.quests;

import org.springframework.stereotype.Repository;
import com.example.festquestbackend.models.quests.Duty;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DutyRepository extends JpaRepository<Duty,Long> {



}
