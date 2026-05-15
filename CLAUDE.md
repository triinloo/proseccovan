# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Structure

```
proseccovan/
├── CLAUDE.md                           # Üldised juhised Claude Code'ile (projekti tase)
├── backend/                            # Serveri lähtekood (Spring Boot)
│   ├── CLAUDE.md                       # Backendi juhised Claude Code'ile (Spring Boot, Java)
│   ├── gradle/                         # Gradle wrapper failid
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── ee.bcs.projektinimi/            # Baaspakett
│   │   │   │       ├── controller/                 # REST kontrollerid
│   │   │   │       │   └── ressursipakett/         # Kontrolleri alampakett (nt user)
│   │   │   │       │       ├── dto/                # Andmeedastuse objektid (DTO-d)
│   │   │   │       │       └── SomeController.java # REST kontroller (nt UserController.java)
│   │   │   │       ├── infrastructure/             # Ühine infrastruktuur
│   │   │   │       │   ├── error/                  # Veavastuse mudel
│   │   │   │       │   └── exception/              # Kohandatud erindiklassid
│   │   │   │       ├── persistence/                # Andmebaasi entiteedid ja repositooriumid
│   │   │   │       │   └── ressursipakett/         # Entiteedi alampakett (nt user)
│   │   │   │       │       ├── Entity.java         # Entiteedi klass (nt User.java)
│   │   │   │       │       ├── EntityMapper.java   # Mapperi liides
│   │   │   │       │       └── EntityRepository.java # Repositooriumi liides
│   │   │   │       └── service/                    # Äriloogika teenused
│   │   │   └── resources/                          # Rakenduse konfiguratsioon
│   │   └── test/                                   # Ühik- ja integratsioonitestid
│   └── [konfiguratsioonifailid]                    # build.gradle, settings.gradle, gradlew jms
│
├── docs/                       # Dokumentatsioon ja õppematerjalid
│   ├── database/                       # SQL skriptid
│   │   ├── 1_reset_database.sql        # Skeemi kustutamine ja taasloomine
│   │   ├── 2_create.sql                # Tabelite ja seoste loomine
│   │   └── 3_import.sql                # Algsete andmete import
│   └── tasks/                  # Ülesannete kirjeldused
│
└── frontend/                   # Kliendipoolne rakendus
    ├── CLAUDE.md               # Frontendi juhised Claude Code'ile (Vue 3, Vite)
    ├── public/                 # Avalikud staatilised failid (kopeeritakse buildi)
    └── src/                    # Rakenduse lähtekood
        ├── api-services/       # Axios API päringute teenused
        ├── assets/             # Staatilised ressursid (pildid, fondid jms)
        ├── auth/               # Autentimise loogika ja abifunktsioonid
        ├── components/         # Korduvkasutatavad Vue komponendid
        │   ├── common/         # Üldkasutatavad elemendid (nupud, sildid, laadijad)
        │   ├── forms/          # Vormi komponendid (sisendid, validatsioon)
        │   ├── modals/         # Modaalakende komponendid
        │   └── tables/         # Tabelite komponendid
        ├── navigation/         # Navigatsiooni komponendid
        ├── router/             # Vue Router marsruutide konfiguratsioon
        └── views/              # Lehekülgede komponendid (marsruutidega seotud)
```

## Project Overview

Full-stack web application with a Spring Boot backend and Vue 3 frontend. The name **proseccovan** refers to the project itself (likely a banking/finance app — the Spring app name is `bank40back`).

## Architecture

- **Backend** (`/backend`): Spring Boot 4.0.6, Java 21, Gradle, PostgreSQL via JPA, MapStruct for DTO mapping, Lombok, Swagger UI (`/swagger-ui.html`), p6spy for SQL logging
- **Frontend** (`/frontend`): Vue 3 (Composition API), Vite 8, Vue Router 5, Pinia 3, Bootstrap 5, Axios, Phosphor Icons

The frontend proxies all `/api` requests to the backend at `http://localhost:8080`. Frontend runs on port 8081.

## Commands

### Backend (from `/backend`)
```bash
./gradlew bootRun          # Start backend (port 8080)
./gradlew build            # Build JAR
./gradlew test             # Run all tests
./gradlew test --tests "proseccovan.backend.SomeTest#methodName"  # Run single test
```

### Frontend (from `/frontend`)
```bash
npm install                # Install dependencies
npm run dev                # Dev server (port 8081)
npm run build              # Production build
npm run lint               # Run oxlint + eslint (with --fix)
npm run format             # Format with Prettier
```

## Database

PostgreSQL on `localhost/postgres`, user `postgres`, password `student123`. Schema is managed via SQL init scripts (`spring.sql.init.mode=always`, `ddl-auto=none`) — DDL lives in SQL files, not Hibernate auto-generation.

## Key Conventions

**Backend:**
- MapStruct is configured with `unmappedTargetPolicy=IGNORE` and `defaultComponentModel=spring` — mappers are Spring beans injected via `@Autowired`/constructor injection
- SQL queries are logged to stdout by p6spy in formatted blocks (useful for debugging N+1 issues); toggle by swapping the datasource URL in `application.properties` between the p6spy and plain PostgreSQL driver lines
- Lombok is used throughout — expect `@Data`, `@Builder`, `@RequiredArgsConstructor` etc.

**Frontend:**
- `@` alias resolves to `src/`
- State management via Pinia stores (Composition API `defineStore` style)
- API calls go through Axios; `/api` prefix is proxied to backend by Vite
- Lint runs oxlint first, then ESLint — both run with `--fix`