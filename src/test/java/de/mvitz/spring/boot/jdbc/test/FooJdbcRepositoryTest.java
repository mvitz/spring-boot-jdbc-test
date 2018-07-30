package de.mvitz.spring.boot.jdbc.test;

import de.mvitz.spring.boot.jdbc.test.support.EmbeddedPostgresSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EmbeddedPostgresSupport
public class FooJdbcRepositoryTest {

    @Autowired
    FooJdbcRepository fooJdbcRepository;

    @Test
    void storeAndRetrieveWorks() {
        fooJdbcRepository.storeData("ABC");

        String data = fooJdbcRepository.retrieveData("ABC");

        assertThat(data, is(equalTo("ABC")));
    }
}
