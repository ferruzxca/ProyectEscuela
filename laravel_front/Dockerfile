# Build stage
FROM composer:2.7 AS composer
WORKDIR /app
COPY . .
RUN composer install --no-dev --prefer-dist --no-interaction --no-progress
RUN composer dump-autoload --optimize

FROM node:20 AS assets
WORKDIR /app
COPY package.json package-lock.json* yarn.lock* pnpm-lock.yaml* ./
RUN if [ -f package-lock.json ]; then npm ci; elif [ -f yarn.lock ]; then corepack enable && yarn install --frozen-lockfile; elif [ -f pnpm-lock.yaml ]; then corepack enable && pnpm install --frozen-lockfile; else npm install; fi
COPY . .
RUN if [ -f package-lock.json ]; then npm run build; elif [ -f yarn.lock ]; then yarn build; elif [ -f pnpm-lock.yaml ]; then pnpm build; else npm run build; fi

# Runtime stage
FROM php:8.2-cli
WORKDIR /app
RUN apt-get update && apt-get install -y \
    libzip-dev \
    unzip \
 && docker-php-ext-install pdo pdo_mysql \
 && rm -rf /var/lib/apt/lists/*

COPY --from=composer /app /app
COPY --from=assets /app/public /app/public
COPY --from=assets /app/resources /app/resources

COPY start.sh /start.sh
RUN chmod +x /start.sh \
    && chown -R www-data:www-data /app/storage /app/bootstrap/cache

ENV APP_ENV=production
ENV APP_DEBUG=false
ENV PORT=10000

EXPOSE 10000
ENTRYPOINT ["/start.sh"]
