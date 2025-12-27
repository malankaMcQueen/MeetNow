CREATE TABLE user_profile (
      id SERIAL PRIMARY KEY
);

CREATE TABLE category (
      id   SERIAL PRIMARY KEY,
      name VARCHAR NOT NULL
);

CREATE TABLE interest (
      id          SERIAL PRIMARY KEY,
      category_id INT NOT NULL REFERENCES category(id),
      name        VARCHAR NOT NULL
);

CREATE TABLE user_interest (
       user_id     INT NOT NULL REFERENCES user_profile(id),
       interest_id INT NOT NULL REFERENCES interest(id),
       PRIMARY KEY (user_id, interest_id)
);

CREATE TABLE geo_point (
       id        SERIAL PRIMARY KEY,
       latitude  DOUBLE PRECISION NOT NULL,
       longitude DOUBLE PRECISION NOT NULL
);

CREATE TABLE event (
       id           SERIAL PRIMARY KEY,
       geo_point_id INT NOT NULL REFERENCES geo_point(id),
       start_time    TIMESTAMP NOT NULL
);

CREATE TABLE event_interest (
        event_id    INT NOT NULL REFERENCES event(id),
        interest_id INT NOT NULL REFERENCES interest(id),
        PRIMARY KEY (event_id, interest_id)
);

CREATE TABLE action_type (
         id     SERIAL PRIMARY KEY,
         name   VARCHAR NOT NULL,
         weight DOUBLE PRECISION NOT NULL
);

CREATE TABLE user_action (
         id              SERIAL PRIMARY KEY,
         user_id         INT NOT NULL REFERENCES user_profile(id),
         event_id        INT NOT NULL REFERENCES event(id),
         action_type_id  INT NOT NULL REFERENCES action_type(id),
         action_time     TIMESTAMP NOT NULL
);
