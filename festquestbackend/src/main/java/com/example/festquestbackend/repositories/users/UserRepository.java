package com.example.festquestbackend.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festquestbackend.models.users.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
