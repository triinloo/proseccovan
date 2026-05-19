# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
./gradlew bootRun          # Start backend on port 8080
./gradlew build            # Compile and package
./gradlew test             # Run all tests
./gradlew test --tests "proseccovan.backend.SomeTest#methodName"  # Run single test
```

## Stack

Spring Boot 4.0.6 · Java 21 · Gradle · PostgreSQL · JPA/Hibernate · MapStruct · Lombok · Swagger (`/swagger-ui.html`) · p6spy

## Database

PostgreSQL on `localhost/postgres` (user: `postgres`, pw: `student123`). Schema is managed via SQL init scripts — `ddl-auto=none`, `spring.sql.init.mode=always`. Never rely on Hibernate to create or alter tables.

To toggle SQL logging: swap the `spring.datasource.url` and `driverClassName` lines in `application.properties` between the p6spy variant and the plain PostgreSQL driver.

## Key Conventions

- **MapStruct**: configured with `defaultComponentModel=spring` — all mappers are Spring beans. Use constructor or `@Autowired` injection. `unmappedTargetPolicy=IGNORE` means unmapped target fields silently stay null.
- **Lombok**: use `@Data`, `@Builder`, `@RequiredArgsConstructor` etc. throughout. Avoid writing boilerplate getters/setters/constructors by hand.
- **Package structure**: root package is `proseccovan.backend`. Organize by feature (e.g. `proseccovan.backend.account`, `proseccovan.backend.user`), not by layer.
- **Swagger documentation**: every controller endpoint MUST have `@Operation` and `@ApiResponses` annotations. `@Operation` must include both `summary` (lühikirjeldus koos tagastatavate väljadega) and `description` (käitumine ja veatingimuseed). `@ApiResponse` must cover every HTTP status the endpoint can return; error responses must reference `ApiError.class` via `@Content(schema = @Schema(implementation = ApiError.class))`. Example:
  ```java
  @Operation(summary = "Sisse logimine. Tagastab userId ja roleName",
          description = """
                  Süsteemist otsitakse username ja password abil kasutajat, kelle konto on ka aktiivne.
                  Kui vastet ei leita visatakse viga errorCode'ga 111""")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "OK"),
          @ApiResponse(responseCode = "403",
                  description = "Vale kasutajanimi või parool",
                  content = @Content(schema = @Schema(implementation = ApiError.class)))})
  ```