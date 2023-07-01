package com.example.techconnect.repositories;

import com.example.techconnect.models.Event;
import com.example.techconnect.models.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {


    // This method helps us find events by UserID

    List<Event> findAllByHostId(long id);

    List<Event> findAllByInterest_Id(long id);


    @Query("from Event where location like ?1")
    List<Event> findEventByLocation(String location);

    List<Event> findEventByTitleContainingIgnoreCase(String keyword);


    @Query("SELECT e FROM Event e WHERE e.host.id <> :userId")
    List<Event> findAllByHostIdNot(@Param("userId") Long userId);





}

