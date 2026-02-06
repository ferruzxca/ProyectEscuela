# Laravel Front (Carátulas) - Scaffold para XAMPP

Este ZIP NO incluye el framework completo (vendor/). Es un **paquete de archivos** para que los pegues dentro de tu proyecto Laravel existente.

## 1) Requisitos
- Tener un proyecto Laravel creado (ej: `laravel new front` o `composer create-project laravel/laravel front`)
- XAMPP corriendo (Apache)
- PHP compatible con tu versión de Laravel
- Tu API Spring Boot corriendo (local o deploy)

## 2) Dónde pegar cada carpeta
Copia y reemplaza/mezcla así:

- `routes/web.php`  -> `TU_PROYECTO/routes/web.php`
- `app/Http/Controllers/*` -> `TU_PROYECTO/app/Http/Controllers/`
- `resources/views/*` -> `TU_PROYECTO/resources/views/`
- `config/services.php` -> agrega el bloque `spring_api` al final del archivo en `TU_PROYECTO/config/services.php`

## 3) .env
En tu `.env` agrega:

SPRING_API_BASE_URL=http://localhost:8080/api
SPRING_API_TIMEOUT=8

## 4) Ejecutar en XAMPP
Laravel debe apuntar a la carpeta `public/`. Opciones:

### Opción A (recomendada): VirtualHost (Apache) apuntando a `public`
Crea un vhost que apunte a:
`C:\xampp\htdocs\TU_PROYECTO\public`

### Opción B: Acceso por ruta (si no quieres vhost)
Abre en navegador:
http://localhost/TU_PROYECTO/public

## 5) Endpoints esperados (para tu compañero del API)
Este front espera estos endpoints JSON:

GET    /alumnos
POST   /alumnos
PATCH  /alumnos/{id}/activar
DELETE /alumnos/{id}

GET    /grupos
POST   /grupos

GET    /catalogos/carreras
GET    /catalogos/turnos
GET    /catalogos/grados
POST   /catalogos/carreras
POST   /catalogos/turnos
POST   /catalogos/grados
DELETE /catalogos/carreras/{id}
PATCH  /catalogos/carreras/{id}/activar

Si tu API usa rutas distintas, solo cambia las URLs en los controladores.

Listo ✅
