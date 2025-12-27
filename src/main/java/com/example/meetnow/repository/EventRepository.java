package com.example.meetnow.repository;

import com.example.meetnow.repository.mapper.InterestMapReducer;
import com.example.meetnow.repository.mapper.RankableEventReducer;
import com.example.meetnow.service.event.calculator.EventDto;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.event.RankableEvent;
import org.jdbi.v3.sqlobject.config.KeyColumn;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterCollector;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface EventRepository {

    @SqlQuery("""
                SELECT e.id AS event_id,
                       e.start_time AS start_time,
                       gp.latitude AS latitude,
                       gp.longitude AS longitude,
                       i.id AS interest_id
                FROM event e
                JOIN geo_point gp ON gp.id = e.geo_point_id
                LEFT JOIN event_interest ei ON ei.event_id = e.id
                LEFT JOIN interest i ON i.id = ei.interest_id
            """)
    @UseRowReducer(RankableEventReducer.class)
    List<RankableEvent> findAllRankableEvents();

    Set<EventDto> findAllByIds(List<Long> eventIds);

    List<Interest> findInterestsByEventId(Long eventId);

    @SqlQuery("""
                SELECT
                    e.id AS event_id,
                    i.id AS interest_id,
                    i.name AS interest_name,
                    i.category_id AS category_id
                FROM event e
                LEFT JOIN event_interest ei ON e.id = ei.event_id
                LEFT JOIN interest i ON i.id = ei.interest_id
                WHERE e.id IN (<eventIds>)
            """)
    @UseRowReducer(InterestMapReducer.class)
    Map<Long, List<Interest>> findInterestsByEventIds(@BindList Set<Long> eventIds);

    @SqlQuery("SELECT event_id, id, title FROM interests WHERE event_id IN (<eventIds>)")
    @RegisterBeanMapper(Interest.class)
    List<Interest> _findInterestsInternal(@BindList("eventIds") Set<Long> eventIds);

}
