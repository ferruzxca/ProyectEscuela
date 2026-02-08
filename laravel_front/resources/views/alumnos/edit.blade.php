@extends('layouts.app')

@section('title', 'Editar Alumno')

@section('content')
  <div class="grid gap-6 lg:grid-cols-2">
    <section class="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-xl">
      <h1 class="text-2xl font-semibold tracking-tight">Editar Alumno</h1>
      <p class="mt-1 text-slate-300">Actualiza los datos del alumno.</p>

      <form class="mt-6 space-y-4" method="POST" action="{{ route('alumnos.update', $alumno['id']) }}">
        @csrf
        @method('PUT')

        <div>
          <label class="text-sm text-slate-300">Matrícula</label>
          <input name="matricula" value="{{ old('matricula', $alumno['matricula'] ?? '') }}"
                 class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500" />
        </div>

        <div>
          <label class="text-sm text-slate-300">Nombre</label>
          <input name="nombre" value="{{ old('nombre', $alumno['nombre'] ?? '') }}"
                 class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500" />
        </div>

        <div>
          <label class="text-sm text-slate-300">Apellido Paterno</label>
          <input name="apellido_paterno" value="{{ old('apellido_paterno', $alumno['apellido_paterno'] ?? '') }}"
                 class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500" />
        </div>

        <div>
          <label class="text-sm text-slate-300">Apellido Materno</label>
          <input name="apellido_materno" value="{{ old('apellido_materno', $alumno['apellido_materno'] ?? '') }}"
                 class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500" />
        </div>

        <div>
          <label class="text-sm text-slate-300">Sel. Grupo</label>
          <select name="grupo_id"
                  class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500">
            <option value="">-- Selecciona --</option>
            @foreach (($grupos ?? []) as $g)
              @php $inactive = isset($g['activo']) && !$g['activo']; @endphp
              <option value="{{ $g['id'] ?? '' }}"
                      @selected(old('grupo_id', $alumno['grupoId'] ?? $alumno['grupo_id'] ?? '') == ($g['id'] ?? ''))>
                {{ $g['codigo'] ?? ($g['grupoCodigo'] ?? 'Grupo') }}{{ $inactive ? ' (inactivo)' : '' }}
              </option>
            @endforeach
          </select>
        </div>

        <button class="w-full rounded-2xl bg-gradient-to-r from-sky-500 to-fuchsia-500 px-5 py-3 font-semibold hover:opacity-90">
          Guardar
        </button>
      </form>
    </section>
  </div>
@endsection
