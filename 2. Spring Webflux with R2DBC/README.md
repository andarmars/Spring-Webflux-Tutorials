# Server/Database

Two ways to configure Repositories

- R2dbcRepository (Preferred for our projects)
- DatabaseClient

1. Dependencies:

- Spring Boot starter spring-boot-starter-data-r2dbc 

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-r2dbc</artifactId>
    </dependency>

- Add an R2DBC driver for the specific database you are using, for example, MySQL. //NB I had to change database from MySQL to PosgreSQL

    <dependency>
        <groupId>com.github.jasync-sql</groupId>
        <artifactId>jasync-r2dbc-mysql</artifactId>
        <version>2.2.3</version>
    </dependency>

1. Properties file:

Locate the file named application.properties under the src/main/resources folder, copy, and paste the following:

        #spring.data.r2dbc.repositories.enabled=true #Enabled by default
        spring.r2dbc.url=r2dbc:<driver>://<host>:<port>/<database>
        spring.r2dbc.username=user
        spring.r2dbc.password=password

Don't forget to replace user and password with the PosgrSQL username and password.

1. Configuring Classes:

- Under src/main/java/package-name, create two folders namely 'model' and 'repository'.
- Under the Model folder, create UserEntity class, and under Repository, create UserRepository interface.

UserEntity:

        import lombok.*;

        import org.springframework.data.annotation.Id;
        import org.springframework.data.relational.core.mapping.Column;
        import org.springframework.data.relational.core.mapping.Table;

        @Data
        @ToString
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @Table("users")
        public class UserEntity {

            @Id
            private Long id;

            private String firstname;

            private String lastname;
        }

UserRepository:

        import org.springframework.data.r2dbc.repository.R2dbcRepository;

        import com.kapelles.tzm.server.model.UserEntity;

        import reactor.core.publisher.Flux;
        import reactor.core.publisher.Mono;

        public interface UserRepository extends R2dbcRepository<UserEntity, Long> {

            Flux<UserEntity> findByFirstnameContaining(String name);

            Mono<UserEntity> findById(Long id);
        }

- Add a pom dependency for Lombok to avoid errors on the Entity Class:

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

### schema.sql

Unlike JPA, Spring Data R2dbc does not maintain the database schemas, so they must be created manually. Spring Data R2dbc provides a ConnectionFactoryInitializer to allow execution of SQL scripts on the database when connected. In a Spring Boot application, it is configured for you automatically. When the application is starting up, it will scan schema.sql and data.sql files in the classpath to initialize the database.

Under the resources folder, create a file called schema.sql and create a PostgreSQL table according to the Entity Model. An example would be as follows:


        CREATE TABLE IF NOT EXISTS tablename (id SERIAL PRIMARY KEY, variablename VARCHAR(255));

### On Application.java file

(a) Enable WebFlux by putting @EnableWebFlux at the top of this class. R2dbcRepositories are enabled by default, so no need to include //@EnableR2dbcRepositories.

(b) For database configuration, on this file you must initialize the database:

        @Bean
        ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);
            initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
            return initializer;
        }

- On CMD SQL shell, create the database.


## Run the application:

- After running the application, check if the table was created in the database:

        use databasename;
        show tables;
        show columns from tablename;
        select * from tablename;





























# Server/Database

## Two ways to configure Repositories

- R2dbcRepository (Preffered for our projects)
- DatabseClient

1. Dependencies

- Spring Boot starter spring-boot-starter-data-r2dbc

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-r2dbc</artifactId>
        </dependency>

- Add a r2dbc driver for the specific database you are using https://r2dbc.io/drivers/, mysql in this case
        <dependency>
			<groupId>com.github.jasync-sql</groupId>
			<artifactId>jasync-r2dbc-mysql</artifactId>
			<version>2.2.3</version>
		</dependency>

2. Properties file

Locate file named application.properties under src/main/resources folder, copy and paste the following:

        #spring.data.r2dbc.repositories.enabled=true #Enabled by default
        spring.r2dbc.url=r2dbc:<driver>://<host>:<port>/<database>
        spring.r2dbc.username=user
        spring.r2dbc.password=password

And dont forget to replace user and password with mysql username and password.

3. Configuring Classes

- under src/main/java/package-name create two folders namely 'model' and 'repository'
- Under Model folder create UserEntity class and under Repository UserRepository interface.

UserEntity:

        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import lombok.ToString;

        import org.springframework.data.annotation.Id;
        import org.springframework.data.relational.core.mapping.Column;
        import org.springframework.data.relational.core.mapping.Table;

        @Data
        @ToString
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @Table("users")
        public class UserEntity {

            @Id
            private Long id;

            private String firstname;

            private String lastname;
        }

UserRepository:

        import org.springframework.data.r2dbc.repository.R2dbcRepository;

        import com.kapelles.tzm.server.model.UserEntity;

        import reactor.core.publisher.Flux;
        import reactor.core.publisher.Mono;

        public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
            
            Flux<UserEntity> findByFirstnameContaining(String name);
            
            @SuppressWarnings("null")
            Mono<UserEntity> findById(Long id);
        }
- Add pom dependency for lombok to avoid error on Entity Class:

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

## schema.sql

Unlike JPA, Spring Data R2dbc does not maintain the database schemas, so they must be created manually. Spring Data R2dbc provides a ConnectionFactoryInitializer to allow execution of sql scripts on database when connected. In a Spring Boot application, it is configured for you automatically. When the application is starting up, it will scan schema.sql and data.sql files in the classpath to initialize the database.

Under resources folder create file called schema.sql and create mysql table according to the Entity Model. An example would be as follows:

        CREATE TABLE IF NOT EXISTS tablename (id SERIAL PRIMARY KEY, variablename VARCHAR(255));

## On Start Application.java file:


    (a) Enable WebFlux by putting at @EnableWebFlux at the top of this class.
        R2dbcRepositories are enabled by default, and so no need to include //@EnableR2dbcRepositories 
    (b) For database configuration, on this file you must initialise database 

        @Bean
        ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);
            initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
            return initializer;
        }

- On cmd sql shell and create database


## Run the application

- Before running this application make sure PostreSQL is running

- After run the application check if the table was created on the database

            use databasename;
            show tables;
            show columns from tablesname;
            select * from tablename;
            

