package com.example.techconnect.repositories;

import com.example.techconnect.models.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee,Long> {






}
