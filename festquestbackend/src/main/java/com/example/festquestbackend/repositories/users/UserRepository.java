package com.example.festquestbackend.repositories.users;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.festquestbackend.models.users.User;

import com.example.festquestbackend.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);


}