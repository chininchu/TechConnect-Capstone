package com.example.techconnect.repositories;

import com.example.techconnect.models.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {


}
