# ProyectEscuela — Backend + Frontend en Render

Sistema escolar para **registro de alumnos**, **grupos** y **catálogos** (carreras, turnos, grados).  
Backend en **Spring Boot** y frontend en **Laravel**, desplegados en **Render** con MySQL externo (Aiven).

Repo: `https://github.com/ferruzxca/ProyectEscuela`

## Stack
- **Backend:** Java 17, Spring Boot 3, JPA/Hibernate, MySQL 8
- **Frontend:** Laravel 12, Vite, Blade
- **Deploy:** Render (Docker), Aiven MySQL

## Estructura del repo
```
/src                -> API Spring Boot
/laravel_front      -> Frontend Laravel
/laravel_front_scaffold (no usado)
```

## Endpoints principales (API)
- `GET /api/carreras`
- `GET /api/turnos`
- `GET /api/grados`
- `GET /api/grupos`
- `GET /api/alumnos`
- `GET /api/health` (estado del API y DB)
- `GET /` (dashboard HTML)

### CRUD
- `POST /api/carreras` `{ nombre, sigla }`
- `POST /api/turnos` `{ nombre, sigla }`
- `POST /api/grados` `{ nombre, numero }`
- `POST /api/grupos` `{ carreraId, turnoId, gradoId }`
- `POST /api/alumnos` `{ matricula, nombre, apellidos, grupoId }`
- `PUT /api/{recurso}/{id}`
- `PATCH /api/{recurso}/{id}/activar`
- `PATCH /api/{recurso}/{id}/inactivar`

Por defecto los GET listan **solo activos**.  
Para incluir inactivos: `GET /api/{recurso}?includeInactivos=true`

## Variables de entorno (Backend)
```
SPRING_DATASOURCE_URL=jdbc:mysql://HOST:PUERTO/DB?sslMode=REQUIRED&allowPublicKeyRetrieval=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=USUARIO
SPRING_DATASOURCE_PASSWORD=PASSWORD
JAVA_VERSION=17
```

## Variables de entorno (Frontend)
```
APP_ENV=production
APP_DEBUG=false
APP_URL=https://TU-URL-FRONT
SPRING_API_BASE_URL=https://TU-URL-BACK
VITE_API_URL=https://TU-URL-BACK
LOG_CHANNEL=stderr
```

---
# Deploy en Render

## Backend (Spring Boot)
1) Render → New Web Service → repo `ProyectEscuela`
2) **Language:** Docker  
3) **Branch:** `main`  
4) Variables (arriba)
5) Deploy

Verifica:
- `https://TU-URL-BACK/` (dashboard)
- `https://TU-URL-BACK/api/health`

## Frontend (Laravel)
1) Render → New Web Service → repo `ProyectEscuela`
2) **Language:** Docker  
3) **Root Directory:** `laravel_front`
4) Variables (arriba)
5) Deploy

---
# Flujo recomendado
1) Crear **Carreras**, **Turnos**, **Grados**
2) Crear **Grupos**
3) Crear **Alumnos**
4) Inactivar registros desde listas (se marcan en rojo tenue)

---
# Desarrollo local (opcional)

## Backend
```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/proyectescuela?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD=""
mvn spring-boot:run
```

## Frontend
```bash
cd laravel_front
composer install
cp .env.example .env
php artisan key:generate
npm install
npm run build
php artisan serve
```

---
# Estado actual
- Backend funcional con dashboard y health check.
- Frontend funcional (CRUD, edición de alumnos, estados activo/inactivo).
- Registros inactivos visibles en rojo tenue.

1) Crear un Web Service en Render desde este repo.
2) Build command: `mvn -q -DskipTests package`
3) Start command: `java -jar target/proyectescuela-0.0.1-SNAPSHOT.jar`
4) Definir variables de entorno (`SPRING_DATASOURCE_*`).
