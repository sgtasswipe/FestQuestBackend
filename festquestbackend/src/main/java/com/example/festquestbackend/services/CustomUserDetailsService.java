package com.example.festquestbackend.services;

import com.example.festquestbackend.models.users.FestUser;
import com.example.festquestbackend.repositories.users.FestUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final FestUserRepository festUserRepository;

    public CustomUserDetailsService(FestUserRepository festUserRepository) {
        this.festUserRepository = festUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password = null;
        List<GrantedAuthority> authorityList = null;
        Optional<FestUser> optionalUser = festUserRepository.findByEmail(username);

        if (optionalUser.isPresent()) {
            userName = optionalUser.get().getEmail();
            password = optionalUser.get().getPassword();
            authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(optionalUser.get().getRole()));
        } else {
            throw new UsernameNotFoundException("User details not found for the user: " + username);
        }
        return new User(username, password, authorityList);
    }
}

