package com.example.meetnow;

import com.example.meetnow.repository.EventRepository;
import com.example.meetnow.service.model.Interest;
import org.junit.jupiter.api.*;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Map;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventDaoTest {

    private PostgreSQLContainer<?> postgres;
    private Jdbi jdbi;
    private EventRepository eventDao;

    @BeforeAll
    void setup() {
        // старт контейнера Postgres
//        postgres = new PostgreSQLContainer<>("postgres:16")
//                .withDatabaseName("testdb")
//                .withUsername("postgres")
//                .withPassword("postgres");
//        postgres.start();
        String jdbcUrl = "jdbc:postgresql://localhost:5432/meet_now";
        String username = "admin";
        String password = "admin";
        // создаём Jdbi
        jdbi = Jdbi.create(jdbcUrl, username, password);
        jdbi.installPlugin(new SqlObjectPlugin());

        // регистрируем DAO
        eventDao = jdbi.onDemand(EventRepository.class);

        // создаём таблицы и тестовые данные
        jdbi.useHandle(handle -> {
//            handle.execute("""
//                CREATE TABLE event (
//                    id BIGINT PRIMARY KEY,
//                    name TEXT
//                );
//                CREATE TABLE interest (
//                    id BIGINT PRIMARY KEY,
//                    name TEXT,
//                    category_id BIGINT
//                );
//                CREATE TABLE event_interest (
//                    event_id BIGINT,
//                    interest_id BIGINT,
//                    PRIMARY KEY (event_id, interest_id)
//                );
//            """);

            // вставляем тестовые данные
            handle.execute("TRUNCATE TABLE event, geo_point, event_interest, category CASCADE;");
            handle.execute("INSERT INTO geo_point (id, latitude, longitude) VALUES (1,1, 1),(2,2, 1);");
            handle.execute("INSERT INTO event (id, geo_point_id, start_time) VALUES (1,1, '2025-05-11'),(2,2, '2025-05-11');");
            handle.execute("INSERT INTO category (id, name) VALUES (100,'I1'),(200,'I2');");
            handle.execute("INSERT INTO interest (id, name, category_id) VALUES (10,'I1',100),(20,'I2',200);");
            handle.execute("INSERT INTO event_interest (event_id, interest_id) VALUES (1,10),(1,20),(2,10);");
        });
    }

//    @AfterAll
//    void teardown() {
//        postgres.stop();
//    }

    @Test
    void testFindInterestsByEventIds() {
        Set<Long> eventIds = Set.of(1L, 2L);
        Map<Long, List<Interest>> result = eventDao.findInterestsByEventIds(eventIds);

        System.out.println(eventDao);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(2, result.get(1L).size());
        Assertions.assertEquals(1, result.get(2L).size());

        System.out.println(result);
    }
}
