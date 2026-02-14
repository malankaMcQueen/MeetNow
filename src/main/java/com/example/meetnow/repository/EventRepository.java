package com.example.meetnow.repository;

import com.example.meetnow.repository.projection.EventInterestProjection;
import com.example.meetnow.repository.projection.RankableEventProjection;
import com.example.meetnow.service.event.calculator.time.OptimalTimeSegment;
import com.example.meetnow.service.model.event.Event;
import com.example.meetnow.service.model.event.RankableEvent;
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

    // todo  мб jpql?
//    @Query(value = """
//                SELECT e.id AS event_id,
//                       e.start_time AS start_time,
//                       gp.latitude AS latitude,
//                       gp.longitude AS longitude,
//                       i.id AS interest_id
//                FROM event e
//                JOIN geo_point gp ON gp.id = e.geo_point_id
//                LEFT JOIN event_interest ei ON ei.event_id = e.id
//                LEFT JOIN interest i ON i.id = ei.interest_id
//            """, nativeQuery = true)
//    List<RankableEventProjection> findAllRankableEvents();

//    @Query("""
//    SELECT DISTINCT new com.example.meetnow.service.model.event.RankableEvent(
//        e.id,
//        e.coordinates,
//        e.startTime,
//        e.interests
//    )
//    FROM Event e
//    JOIN FETCH e.coordinates gp
//    LEFT JOIN FETCH e.interests i
//    """)
//    List<RankableEvent> findAllRankableEvents();
//


//    @Query("""
//    SELECT
//        e.id as id,
//        e.coordinates as coordinates,
//        e.startTime as startTime,
//        e.interests as interests
//    FROM Event e
//    WHERE startTime > CURRENT_TIMESTAMP
//    """)
//    List<RankableEventProjection> findAllRankableEvents();

    @EntityGraph(attributePaths = {"coordinates", "interests"})
    List<RankableEventProjection> findByStartTimeAfter(LocalDateTime time);


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
               i as interest
        FROM Event e
        LEFT JOIN e.interests i
        WHERE e.id IN :eventIds
        """)
    List<EventInterestProjection> findInterestsByEventIds(@Param("eventIds") Set<Long> eventIds);
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