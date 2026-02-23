package com.example.meetnow.service.repository;

import com.example.meetnow.service.repository.projection.EventInterestProjection;
import com.example.meetnow.service.repository.projection.RankableEventProjection;
import com.example.meetnow.service.model.event.Event;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @EntityGraph(attributePaths = {"coordinates", "interests", "interests.category"})
    List<RankableEventProjection> findByStartTimeAfterAndActiveIsTrue(LocalDateTime time);


//    @Query("""
//    SELECT new com.example.meetnow.dto.RankableEventDTO(
//        e.id,
//        new com.example.meetnow.service.model.GeoPoint(
//            null,
//            gp.latitude,
//            gp.longitude
//            ),
//        e.startTime,
//        e.interests
//    )
//    FROM Event e
//    JOIN e.coordinates gp
//    """)
//    List<RankableEvent> findRankableEvents();

    @Query("""
        SELECT e.id as eventId,
               i as interest,
               c as category
        FROM Event e
        LEFT JOIN e.interests i
        LEFT JOIN FETCH i.category c
        WHERE e.id IN :eventIds
        """)
    // todo тут чет делал может не работать
    List<EventInterestProjection> findInterestsByEventIds(@Param("eventIds") Set<Long> eventIds);

    @Query("""
        SELECT DISTINCT e
        FROM Event e
        LEFT JOIN FETCH e.participants
        WHERE e.id = :id
        """)
    Optional<Event> findWithAllDataById(Long id);

    @EntityGraph(attributePaths = {"participants"})
    Optional<Event> findWithParticipantsById(Long eventId);

    @Query("""
        SELECT DISTINCT e
        FROM Event e
        LEFT JOIN FETCH e.participants
        LEFT JOIN FETCH e.organizer o
        WHERE o.id = :ownerId
        """)
    List<Event> findByOrganizerId(Long ownerId);


    @Query("""
    SELECT e
    FROM Event e
    JOIN FETCH e.participants p
    WHERE p.id = :userId
""")
    List<Event> findAllByParticipantId(Long userId);

    //    @SqlQuery("""
//                SELECT e.id AS event_id,
//                       e.start_time AS start_time,
//                       gp.latitude AS latitude,
//                       gp.longitude AS longitude,
//                       i.id AS interest_id
//                FROM event e
//                JOIN geo_point gp ON gp.id = e.geo_point_id
//                LEFT JOIN event_interest ei ON ei.event_id = e.id
//                LEFT JOIN interest i ON i.id = ei.interest_id
//            """)
//    Map<Long, List<Interest>> findInterestsByEventIds(Set<Long> eventIds);
//    @SqlQuery("""
//                SELECT
//                    e.id AS event_id,
//                    i.id AS interest_id,
//                    i.name AS interest_name,
//                    i.category_id AS category_id
//                FROM event e
//                LEFT JOIN event_interest ei ON e.id = ei.event_id
//                LEFT JOIN interest i ON i.id = ei.interest_id
//                WHERE e.id IN (<eventIds>)
//            """)
}