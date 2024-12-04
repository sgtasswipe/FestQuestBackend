package com.example.festquestbackend.repositories.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.festquestbackend.models.users.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}