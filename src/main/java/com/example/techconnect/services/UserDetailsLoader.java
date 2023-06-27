package com.example.techconnect.services;

import com.example.techconnect.models.User;
import com.example.techconnect.models.UserWithRoles;
import com.example.techconnect.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {
    private final UserRepository users;

    public UserDetailsLoader(UserRepository users) {
        this.users = users;
    }


    // The method below is coming from the UserDetailsService interface.
    // Which we must define ourselves
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // The findByUsername method needs to be established in our UserRepository
        User user = users.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        return new UserWithRoles(user);
    }
}
