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
            @php $inactive = isset($c['activo']) && !$c['activo']; @endphp
            <option value="{{ $c['id'] ?? '' }}" @selected(old('carrera_id') == ($c['id'] ?? ''))>
              {{ $c['nombre'] ?? 'Carrera' }}{{ $inactive ? ' (inactiva)' : '' }}
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
            @php $inactive = isset($t['activo']) && !$t['activo']; @endphp
            <option value="{{ $t['id'] ?? '' }}" @selected(old('turno_id') == ($t['id'] ?? ''))>
              {{ $t['nombre'] ?? 'Turno' }}{{ $inactive ? ' (inactivo)' : '' }}
            </option>
          @endforeach
        </select>
      </div>

      <div>
        <label class="text-sm text-slate-300">Grado</label>
        <select name="grado_id"
                class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-fuchsia-500">
          <option value="">-- Selecciona --</option>
          @foreach (($grados ?? []) as $g)
            @php $inactive = isset($g['activo']) && !$g['activo']; @endphp
            <option value="{{ $g['id'] ?? '' }}" @selected(old('grado_id') == ($g['id'] ?? ''))>
              {{ $g['nombre'] ?? 'Grado' }}{{ isset($g['numero']) ? ' ('.$g['numero'].')' : '' }}{{ $inactive ? ' (inactivo)' : '' }}
            </option>
          @endforeach
        </select>
      </div>

      <div class="sm:col-span-2">
        <button class="w-full rounded-2xl bg-gradient-to-r from-fuchsia-500 to-amber-400 px-5 py-3 font-semibold hover:opacity-90">
          Registrar
        </button>
      </div>
    </form>
  </section>
@endsection
