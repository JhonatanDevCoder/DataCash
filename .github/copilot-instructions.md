## Quick context for AI coding agents

- Project: Spring Boot application (entry: `com.parqueadero.gestion_parqueadero.GestionParqueaderoApplication`).
- Build system: Maven wrapper present (`mvnw`, `mvnw.cmd`). Use the wrapper to ensure correct Maven version.
- JVM: Java 21 (see `pom.xml` property `java.version`).
- Frameworks: Spring Boot 3.x, Spring Data JPA, Spring Web. Runtime dependency: MySQL (connector present).
- Config: `src/main/resources/application.properties` contains the active DB settings (MySQL at `jdbc:mysql://localhost:3306/parqueadero`).

## Big-picture architecture (what to know quickly)

- Minimal current codebase: a single Spring Boot starter class at `src/main/java/com/parqueadero/gestion_parqueadero/GestionParqueaderoApplication.java`.
- Typical components will live under the package `com.parqueadero.gestion_parqueadero`:
  - Entities: `src/main/java/com/parqueadero/gestion_parqueadero/*.java` (look for `@Entity` classes when added).
  - Repositories: interfaces extending `JpaRepository` (package-level convention).
  - Controllers: REST controllers annotated with `@RestController` under the same base package.
- Data flow: incoming HTTP → Controller → Service (optional) → Repository (Spring Data JPA) → MySQL. The project currently configures Hibernate to auto-update the schema (`spring.jpa.hibernate.ddl-auto=update`).

## Build, run and test (concrete commands)

Use the Maven wrapper in the repo root to ensure reproducible builds.

- Run application (Windows PowerShell):
  - Start in dev mode with auto-restart: `./mvnw.cmd spring-boot:run` (PowerShell: `.
    mvnw.cmd spring-boot:run`).
  - Build a production jar: `./mvnw.cmd -DskipTests package` then run: `java -jar target\gestion-parqueadero-0.0.1-SNAPSHOT.jar`.
  - Run tests: `./mvnw.cmd test`.

- Debug locally (remote JDWP attach):
  - `./mvnw.cmd spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"`

## Project-specific conventions & gotchas

- Package name contains an underscore: `gestion_parqueadero`. Keep filesystem path and package declarations consistent when adding classes.
- Database config is in `application.properties` and currently points to MySQL on `localhost:3306` with database `parqueadero` and user `root` (password blank). Secrets are committed here—avoid hardcoding passwords in future changes; prefer environment variables or `application-*.properties`.
- Hibernate is configured with `spring.jpa.hibernate.ddl-auto=update`. This will modify schema at startup. Be explicit about schema changes when adding entities.
- SQL logging is enabled (`spring.jpa.show-sql=true`), useful for small debugging tasks but noisy in production.

## Integration points / external dependencies

- MySQL (runtime): ensure a MySQL instance with a `parqueadero` DB is available for local runs. If unavailable, tests or startup will fail unless you add an alternative profile (H2) or mock the Repository layer.
- No other external services or messaging systems are present in the codebase currently.

## Typical small tasks — examples

- Add a new entity:
  - Create `src/main/java/com/parqueadero/gestion_parqueadero/model/ParkingSpot.java` with `@Entity` and a corresponding `ParkingSpotRepository extends JpaRepository<ParkingSpot,Long>`.
  - Rely on `spring.jpa.hibernate.ddl-auto=update` to create the table locally (verify SQL via `spring.jpa.show-sql`).

- Add a REST endpoint:
  - Create `src/main/java/com/parqueadero/gestion_parqueadero/controller/ParkingController.java` annotated with `@RestController` and map routes under `/api`.

## Files to inspect first when triaging issues

- `pom.xml` — Java version, Spring Boot version, dependencies.
- `src/main/resources/application.properties` — DB and JPA runtime settings (affects startup failures).
- `src/main/java/com/parqueadero/gestion_parqueadero/GestionParqueaderoApplication.java` — application bootstrap and main package.

## When you can't run (common causes)

- MySQL not running or DB `parqueadero` missing → startup exceptions. Either start MySQL, create DB, or temporarily change `application.properties` to use an in-memory DB profile.
- Java version mismatch (requires Java 21). Build will fail on older JDKs.

---
If any section is unclear or you'd like the file to include examples for adding tests, a local H2 profile, or CI pipeline steps, tell me which area to expand and I will update this file.
