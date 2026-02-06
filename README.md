# ProyectEscuela API

API en Spring Boot para registro de alumnos, grupos y catálogos (carreras, turnos, grados).

## GitHub
```text
https://github.com/ferruzxca/ProyectEscuela
```

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
- `PUT /api/{recurso}/{id}`
- `PATCH /api/{recurso}/{id}/activar`
- `PATCH /api/{recurso}/{id}/inactivar`

Por defecto los GET listan solo activos. Para incluir inactivos:\n`GET /api/{recurso}?includeInactivos=true`

## Render
Render no ofrece MySQL administrado; usa un MySQL externo y configura las variables de entorno.

### Opción recomendada: MySQL externo (PlanetScale, Aiven, Clever Cloud, Railway)
1) Crea una base MySQL y copia el `host`, `puerto`, `usuario`, `password` y `database`.
2) En Render crea un **Web Service** desde este repo.
3) Configura:
   - Build command: `mvn -q -DskipTests package`
   - Start command: `java -jar target/proyectescuela-0.0.1-SNAPSHOT.jar`
4) Variables de entorno en Render:
   - `SPRING_DATASOURCE_URL` = `jdbc:mysql://HOST:PUERTO/DB?sslMode=REQUIRED&allowPublicKeyRetrieval=true&serverTimezone=UTC`
   - `SPRING_DATASOURCE_USERNAME` = `USUARIO`
   - `SPRING_DATASOURCE_PASSWORD` = `PASSWORD`
   - `JAVA_VERSION` = `17`
5) Deploy. Al iniciar, `schema.sql` crea las tablas y el trigger.

### Opción alternativa: MySQL en un VPS propio
1) Instala MySQL en tu VPS y abre el puerto 3306 (o túnel privado).
2) Crea base `proyectescuela` y usuario con permisos.
3) Usa esas credenciales en las variables de entorno de Render (igual que arriba).

### Checklist rápido de conexión
- El host acepta conexiones externas.
- El usuario tiene permisos sobre la DB.
- El firewall permite el puerto.

1) Crear un Web Service en Render desde este repo.
2) Build command: `mvn -q -DskipTests package`
3) Start command: `java -jar target/proyectescuela-0.0.1-SNAPSHOT.jar`
4) Definir variables de entorno (`SPRING_DATASOURCE_*`).
