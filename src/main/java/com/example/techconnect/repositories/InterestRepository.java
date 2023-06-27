package com.example.techconnect.repositories;

import com.example.techconnect.models.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest,Long> {


}
