package com.example.techconnect.repositories;

import com.example.techconnect.models.Attendee;
import com.example.techconnect.models.Event;
import com.example.techconnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Long> {


    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false  END FROM Attendee a WHERE a.user = :user AND a.event = :event")
    boolean existsByUserAndEvent(@Param("user") User user, @Param("event") Event event);


//    @Query("SELECT a FROM Attendee a WHERE a.event = :event AND a.user = :user")
//    Optional<Attendee> findByEventAndUser(@Param("event") Event event, @Param("user") User user);

    @Query("SELECT a FROM Attendee a WHERE a.event = :event AND a.user = :user")
    List<Attendee> findByEventAndUser(@Param("event") Event event, @Param("user") User user);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Attendee a WHERE a.user = :user AND a.event.id = :eventId")
    boolean existsByUserAndEventId(@Param("user") User user, @Param("eventId") Long eventId);


    // Events that the user has been registered for


    @Query("SELECT a.event FROM Attendee a WHERE a.user = ?1")
    List<Event> findRegisteredEventsByUser(User user);


}
