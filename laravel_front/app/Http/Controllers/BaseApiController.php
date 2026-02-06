<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Http;

abstract class BaseApiController extends Controller
{
    protected function api()
    {
        $baseUrl = config('services.spring_api.base_url') ?? env('SPRING_API_BASE_URL');
        $baseUrl = is_string($baseUrl) ? rtrim($baseUrl, '/') : '';
        if ($baseUrl === '') {
            throw new \RuntimeException('SPRING_API_BASE_URL no está configurada');
        }
        if (!str_ends_with($baseUrl, '/api')) {
            $baseUrl .= '/api';
        }

        return Http::baseUrl($baseUrl)
            ->timeout(config('services.spring_api.timeout'))
            ->acceptJson();
    }

    protected function apiErrorMessage(\Throwable $e): string
    {
        return "No se pudo conectar con la API (Spring Boot). Detalle: " . $e->getMessage();
    }
}
