# ProyectEscuela API

API en Spring Boot para registro de alumnos, grupos y catálogos (carreras, turnos, grados).

## Requisitos
- Java 17
- MySQL 8+

## Variables de entorno
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `PORT` (opcional)

Ejemplo local:
```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/proyectescuela?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD=""
```

## Correr local
```bash
mvn spring-boot:run
```

## Endpoints
- `GET/POST /api/carreras`
- `GET/POST /api/turnos`
- `GET/POST /api/grados`
- `GET/POST /api/grupos`
- `GET/POST /api/alumnos`
- `PATCH /api/{recurso}/{id}/activar`
- `PATCH /api/{recurso}/{id}/inactivar`

Por defecto los GET listan solo activos. Para incluir inactivos:\n`GET /api/{recurso}?includeInactivos=true`

## Render
Render no ofrece MySQL administrado; usa un MySQL externo y configura las variables de entorno.

1) Crear un Web Service en Render desde este repo.
2) Build command: `mvn -q -DskipTests package`
3) Start command: `java -jar target/proyectescuela-0.0.1-SNAPSHOT.jar`
4) Definir variables de entorno (`SPRING_DATASOURCE_*`).
