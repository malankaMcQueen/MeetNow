package com.example.meetnow;

import com.example.meetnow.service.repository.EventRepository;
import com.example.meetnow.service.repository.UserActionRepository;
import com.example.meetnow.service.model.UserAction;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventDaoTest {

    private PostgreSQLContainer<?> postgres;
    private Jdbi jdbi;
    private EventRepository eventDao;
    private UserActionRepository userActionRepo;

    @BeforeAll
    void setup() {
        // старт контейнера Postgres
        // postgres = new PostgreSQLContainer<>("postgres:16")
        // .withDatabaseName("testdb")
        // .withUsername("postgres")
        // .withPassword("postgres");
        // postgres.start();
        String jdbcUrl = "jdbc:postgresql://localhost:5432/meet_now";
        String username = "admin";
        String password = "admin";
        // создаём Jdbi
        jdbi = Jdbi.create(jdbcUrl, username, password);
        jdbi.installPlugin(new SqlObjectPlugin());

        // регистрируем DAO
        eventDao = jdbi.onDemand(EventRepository.class);
        userActionRepo = jdbi.onDemand(UserActionRepository.class);

        // создаём таблицы и тестовые данные
        jdbi.useHandle(handle -> {
            //
            // вставляем тестовые данные
            handle.execute(
                    "TRUNCATE TABLE event, geo_point, event_interest, user_action, user_profile, action_type, category CASCADE;");
            handle.execute("INSERT INTO geo_point (id, latitude, longitude) VALUES (1,1, 1),(2,2, 1);");
            handle.execute("INSERT INTO user_profile (id) VALUES (1),(2);");
            handle.execute("INSERT INTO action_type (id, name, weight) VALUES (1, '1', 0.7),(2, '2', 0.3);");
            handle.execute(
                    "INSERT INTO event (id, geo_point_id, start_time) VALUES (1,1, '2025-05-11'),(2,2, '2025-05-11');");
            handle.execute("INSERT INTO category (id, name) VALUES (100,'I1'),(200,'I2');");
            handle.execute("INSERT INTO interest (id, name, category_id) VALUES (10,'I1',100),(20,'I2',200);");
            handle.execute("INSERT INTO event_interest (event_id, interest_id) VALUES (1,10),(1,20),(2,10);");
            handle.execute(
                    "INSERT INTO user_action (id, user_id, event_id, action_type_id, action_time) VALUES (1, 1, 1, 1, '2025-05-11'),(2, 2, 2, 2, '2025-05-11');");
        });
    }

    // @AfterAll
    // void teardown() {
    // postgres.stop();
    // }

    @Test
    void testUserActionRepo() {
        // Set<Long> eventIds = Set.of(1L, 2L);
        Set<UserAction> result = userActionRepo.findAllByUserId(1L);

        System.out.println(result);
        // Assertions.assertEquals(2, result.size());
        // Assertions.assertEquals(2, result.get(1L).size());
        // Assertions.assertEquals(1, result.get(2L).size());

    }

//    @Test
//    void testFindInterestsByEventIds() {
//        Set<Long> eventIds = Set.of(1L, 2L);
//        Map<Long, List<Interest>> result = eventDao.findInterestsByEventIds(eventIds);
//
//        System.out.println(eventDao);
//        Assertions.assertEquals(2, result.size());
//        Assertions.assertEquals(2, result.get(1L).size());
//        Assertions.assertEquals(1, result.get(2L).size());
//
//        System.out.println(result);
//    }
}
