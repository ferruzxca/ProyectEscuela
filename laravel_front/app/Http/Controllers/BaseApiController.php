<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Http;

abstract class BaseApiController extends Controller
{
    protected function api()
    {
        return Http::baseUrl(config('services.spring_api.base_url'))
            ->timeout(config('services.spring_api.timeout'))
            ->acceptJson();
    }

    protected function apiErrorMessage(\Throwable $e): string
    {
        return "No se pudo conectar con la API (Spring Boot). Detalle: " . $e->getMessage();
    }
}
