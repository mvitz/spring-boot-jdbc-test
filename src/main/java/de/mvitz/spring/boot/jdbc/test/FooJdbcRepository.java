package de.mvitz.spring.boot.jdbc.test;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static java.util.Collections.singletonMap;

@Repository
public class FooJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FooJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void storeData(String id) {
        jdbcTemplate.update("INSERT INTO foo(id) VALUES (:id)", singletonMap("id", id));
    }

    public String retrieveData(String id) {
        return jdbcTemplate.queryForObject("SELECT id FROM foo WHERE id = :id", singletonMap("id", id), String.class);
    }
}
