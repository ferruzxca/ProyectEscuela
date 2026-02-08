<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AlumnoController;
use App\Http\Controllers\GrupoController;
use App\Http\Controllers\CatalogoController;

Route::get('/', fn() => redirect()->route('alumnos.index'))->name('home');

/* ===== Alumnos ===== */
Route::get('/alumnos', [AlumnoController::class, 'index'])->name('alumnos.index');
Route::get('/alumnos/crear', [AlumnoController::class, 'create'])->name('alumnos.create');
Route::post('/alumnos', [AlumnoController::class, 'store'])->name('alumnos.store');

Route::get('/alumnos/{id}/editar', [AlumnoController::class, 'edit'])->name('alumnos.edit');
Route::put('/alumnos/{id}', [AlumnoController::class, 'update'])->name('alumnos.update');
Route::patch('/alumnos/{id}/activar', [AlumnoController::class, 'activate'])->name('alumnos.activate');
Route::delete('/alumnos/{id}', [AlumnoController::class, 'destroy'])->name('alumnos.destroy');

/* ===== Grupos ===== */
Route::get('/grupos/crear', [GrupoController::class, 'create'])->name('grupos.create');
Route::post('/grupos', [GrupoController::class, 'store'])->name('grupos.store');

/* ===== Catálogos ===== */
Route::get('/catalogos', [CatalogoController::class, 'index'])->name('catalogos.index');

Route::post('/catalogos/carreras', [CatalogoController::class, 'storeCarrera'])->name('catalogos.carreras.store');
Route::post('/catalogos/turnos', [CatalogoController::class, 'storeTurno'])->name('catalogos.turnos.store');
Route::post('/catalogos/grados', [CatalogoController::class, 'storeGrado'])->name('catalogos.grados.store');

Route::delete('/catalogos/carreras/{id}', [CatalogoController::class, 'destroyCarrera'])->name('catalogos.carreras.destroy');
Route::patch('/catalogos/carreras/{id}/activar', [CatalogoController::class, 'activateCarrera'])->name('catalogos.carreras.activate');
