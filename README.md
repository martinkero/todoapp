# todoapp

Basic todoapp (create/complete/delete) built on a Typescript/Svelte frontend, Kotlin/Ktor backend and PostgreSQL DB. Deployed with Docker Compose.

Demo - [todoapp.martinkero.se](https://todoapp.martinkero.se)

Other technologies used:
- Caddy - Serving frontend statically and reverse proxy for backend
- JetBrains/Exposed (DSL mode) for type-safe SQL and easy table management
- H2 in-memory DB for testing

# Prerequisites
- Docker
- Docker Compose

# How to run
- Checkout repo
- `docker volume create --name=caddy-data`
- `docker-compose up todoapp-postgres` (Only necessary to start seperate first time when db initializes)
- `docker-compose up` (env var SITE_ADDRESS specifies site/hostname, defaults to http://localhost)


# Possible improvements
- Feature: Drag and Drop sorting of todo's
- Frontend: Internationalization
- Frontend: Improve responsitivity on slow connections
- Backend: Logging
- Backend: DB Table migration
- Backend: Don't expose internal Todo-ID to frontend
- Backend: Decrease duplication of fields in Todo.kt
- Docker Compose depends_on is not enough to let DB init before starting backend, maybe possible to sleep/check for port?
