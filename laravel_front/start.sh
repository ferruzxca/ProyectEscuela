#!/bin/sh
set -e

if [ ! -f /app/.env ]; then
  cp /app/.env.example /app/.env
fi

php /app/artisan key:generate --force
php /app/artisan config:clear
php /app/artisan route:clear
php /app/artisan view:clear

exec php -S 0.0.0.0:${PORT} -t /app/public
