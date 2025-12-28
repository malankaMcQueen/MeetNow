package com.example.meetnow.repository;

import com.example.meetnow.repository.mapper.UserContextReducer;
import com.example.meetnow.service.model.UserContext;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    @SqlQuery("""
            SELECT
                u.id AS user_id,
                i.id AS interest_id,
                i.name AS interest_name,
                i.category_id AS interest_category_id
            FROM user u
            LEFT JOIN user_interest ui ON ui.user_id = u.id
            LEFT JOIN interest i ON i.id = ui.interest_id
            WHERE u.id = :userId
            """)
    @UseRowReducer(UserContextReducer.class)
    UserContext getUserContextById(@Bind Long userId);
}
