package com.example.meetnow.repository;

import com.example.meetnow.service.model.UserAction;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@RegisterConstructorMapper(UserAction.class)
public interface UserActionRepository {

    @SqlQuery("""
                SELECT
                    a.id AS id,
                    a.user_id AS user_id,
                    a.event_id AS event_id,
                    a.action_time AS action_time,
                    at.id AS at_id,
                    at.name AS at_name,
                    at.weight AS at_weight
                FROM user_action a
                INNER JOIN action_type at ON at.id = a.action_type_id
                WHERE a.id = :id
            """)
    Set<UserAction> findAllByUserId(@Bind("id") Long id);

}
