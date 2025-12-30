package com.example.meetnow.repository;

import com.example.meetnow.repository.mapper.UserContextReducer;
import com.example.meetnow.service.model.User;
import com.example.meetnow.service.model.UserContext;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    @SqlQuery("""
            SELECT
                u.id AS user_id,
                i.id AS interest_id,
                i.name AS interest_name,
                i.category_id AS interest_category_id
            FROM user_profile u
            LEFT JOIN user_interest ui ON ui.user_id = u.id
            LEFT JOIN interest i ON i.id = ui.interest_id
            WHERE u.id = :userId
            """)
    @UseRowReducer(UserContextReducer.class)
    UserContext getUserContextById(@Bind Long userId);

    @SqlUpdate("""
            INSERT INTO user_profile(id) values(:id)
            """)
    @GetGeneratedKeys
    User insert(@BindMethods User user);

    @SqlBatch("""
            INSERT INTO user_interest(user_id, interest_id) values(:userId, :interest)
            """)
    void insertUserInterests(@Bind("userId") Long userId, @Bind("interest") List<Long> interests);
}
