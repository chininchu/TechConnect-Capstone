package com.example.techconnect.repositories;

import com.example.techconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User profilePicture(MultipartFile profilePicture);
}
