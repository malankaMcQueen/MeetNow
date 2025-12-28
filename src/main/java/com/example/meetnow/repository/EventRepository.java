package com.example.meetnow.repository;

import com.example.meetnow.repository.mapper.EventInterestRow;
import com.example.meetnow.repository.mapper.RankableEventReducer;
import com.example.meetnow.service.model.Interest;
import com.example.meetnow.service.model.event.RankableEvent;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RegisterConstructorMapper(EventInterestRow.class)
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

    @SqlQuery("""
                SELECT
                    e.id AS event_id,
                    i.id AS interest_id,
                    i.name AS interest_name,
                    i.category_id AS interest_category_id
                FROM event e
                LEFT JOIN event_interest ei ON e.id = ei.event_id
                LEFT JOIN interest i ON i.id = ei.interest_id
                WHERE e.id IN (<eventIds>)
            """)
    Set<EventInterestRow> findInterestsByEventIdsInList(@BindList Set<Long> eventIds);

    default Map<Long, List<Interest>> findInterestsByEventIds(Set<Long> eventIds) {
        return findInterestsByEventIdsInList(eventIds).stream().filter(row -> row.interest() != null)
                .collect(Collectors.groupingBy(EventInterestRow::eventId,
                        Collectors.mapping(EventInterestRow::interest, Collectors.toList())));
    }

}
