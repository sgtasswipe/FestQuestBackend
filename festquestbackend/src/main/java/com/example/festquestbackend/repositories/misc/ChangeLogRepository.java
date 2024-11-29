package com.example.festquestbackend.repositories.misc;

import org.springframework.stereotype.Repository;
import com.example.festquestbackend.models.misc.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {



}
