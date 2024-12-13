package com.example.festquestbackend.repositories.quests;

import com.example.festquestbackend.models.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestRoleRepository extends JpaRepository<Role, Long> {
}
