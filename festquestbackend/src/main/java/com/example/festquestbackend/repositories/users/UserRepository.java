package com.example.festquestbackend.repositories.users;

import com.example.festquestbackend.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
