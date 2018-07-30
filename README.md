# Spring Boot JDBC Test

This project uses `ru.yandex.qatools.embed:postgresql-embedded` to provide an
embedded version of PostgreSQL for testing. This embedded PostgreSQL is
configured and startet within `EmbeddedPostgresSupport`. However since Spring
Boot 2.0.4.RELEASE this doesn't work any longer. I suspect it has something to
do with https://github.com/spring-projects/spring-boot/issues/13155

## License

spring-boot-jdbc-test is Open Source software released under the
[Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).
