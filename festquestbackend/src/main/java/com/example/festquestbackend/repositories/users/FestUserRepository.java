package com.example.festquestbackend.repositories.users;

import com.example.festquestbackend.models.users.FestUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FestUserRepository extends JpaRepository<FestUser, Long> {
    Optional<FestUser> findByEmail(String email);

}
