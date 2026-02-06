#!/bin/sh
set -e

if [ ! -f /app/.env ]; then
  cp /app/.env.example /app/.env
fi

if [ ! -f /app/database/database.sqlite ]; then
  mkdir -p /app/database
  touch /app/database/database.sqlite
fi

if [ -z "${APP_ENV}" ]; then
  export APP_ENV=production
fi
if [ -z "${APP_DEBUG}" ]; then
  export APP_DEBUG=false
fi
if [ -z "${LOG_CHANNEL}" ]; then
  export LOG_CHANNEL=stderr
fi
if [ -z "${CACHE_STORE}" ]; then
  export CACHE_STORE=file
fi
if [ -z "${SESSION_DRIVER}" ]; then
  export SESSION_DRIVER=file
fi
if [ -z "${QUEUE_CONNECTION}" ]; then
  export QUEUE_CONNECTION=sync
fi
if [ -z "${DB_CONNECTION}" ]; then
  export DB_CONNECTION=sqlite
fi
if [ -z "${DB_DATABASE}" ]; then
  export DB_DATABASE=/app/database/database.sqlite
fi

php /app/artisan key:generate --force
php /app/artisan config:clear
php /app/artisan route:clear

if [ ! -d /app/storage/framework/views ]; then
  mkdir -p /app/storage/framework/views
fi
if [ ! -d /app/storage/framework/cache ]; then
  mkdir -p /app/storage/framework/cache
fi
if [ ! -d /app/storage/framework/sessions ]; then
  mkdir -p /app/storage/framework/sessions
fi
php /app/artisan view:clear

exec php -S 0.0.0.0:${PORT} -t /app/public
