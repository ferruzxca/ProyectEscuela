@extends('layouts.app')

@section('title', 'Registro de Grupos')

@section('content')
  <section class="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-xl max-w-3xl">
    <h1 class="text-2xl font-semibold tracking-tight">Registro de Grupos</h1>
    <p class="mt-1 text-slate-300">Crea un grupo con carrera, turno y grado.</p>

    <form class="mt-6 grid gap-4 sm:grid-cols-2" method="POST" action="{{ route('grupos.store') }}">
      @csrf

      <div class="sm:col-span-2">
        <label class="text-sm text-slate-300">Carrera</label>
        <select name="carrera_id"
                class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-fuchsia-500">
          <option value="">-- Selecciona --</option>
          @foreach (($carreras ?? []) as $c)
            <option value="{{ $c['id'] ?? '' }}" @selected(old('carrera_id') == ($c['id'] ?? ''))>
              {{ $c['nombre'] ?? 'Carrera' }}
            </option>
          @endforeach
        </select>
      </div>

      <div>
        <label class="text-sm text-slate-300">Turno</label>
        <select name="turno_id"
                class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-fuchsia-500">
          <option value="">-- Selecciona --</option>
          @foreach (($turnos ?? []) as $t)
            <option value="{{ $t['id'] ?? '' }}" @selected(old('turno_id') == ($t['id'] ?? ''))>
              {{ $t['nombre'] ?? 'Turno' }}
            </option>
          @endforeach
        </select>
      </div>

      <div>
        <label class="text-sm text-slate-300">Grado</label>
        <select name="grado"
                class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-fuchsia-500">
          <option value="">-- Selecciona --</option>
          @foreach (($grados ?? []) as $g)
            <option value="{{ $g['nombre'] ?? ($g['id'] ?? '') }}" @selected(old('grado') == ($g['nombre'] ?? ($g['id'] ?? '')))>
              {{ $g['nombre'] ?? 'Grado' }}
            </option>
          @endforeach
        </select>
      </div>

      <div class="sm:col-span-2">
        <label class="text-sm text-slate-300">Grupo (clave)</label>
        <input name="grupo" value="{{ old('grupo') }}"
               class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-fuchsia-500"
               placeholder="Ej. ISX1101-V" />
        <p class="mt-2 text-xs text-slate-400">Tu API puede generar esta clave automáticamente si prefieren.</p>
      </div>

      <div class="sm:col-span-2">
        <button class="w-full rounded-2xl bg-gradient-to-r from-fuchsia-500 to-amber-400 px-5 py-3 font-semibold hover:opacity-90">
          Registrar
        </button>
      </div>
    </form>
  </section>
@endsection
