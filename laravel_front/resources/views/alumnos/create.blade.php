@extends('layouts.app')

@section('title', 'Registro de Alumnos')

@section('content')
  <div class="grid gap-6 lg:grid-cols-2">
    <section class="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-xl">
      <h1 class="text-2xl font-semibold tracking-tight">Registro de Alumnos</h1>
      <p class="mt-1 text-slate-300">Captura los datos y registra el alumno en el grupo.</p>

      <form class="mt-6 space-y-4" method="POST" action="{{ route('alumnos.store') }}">
        @csrf

        <div>
          <label class="text-sm text-slate-300">Matrícula</label>
          <input name="matricula" value="{{ old('matricula') }}"
                 class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500"
                 placeholder="Ej. A12345" />
        </div>

        <div>
          <label class="text-sm text-slate-300">Nombre</label>
          <input name="nombre" value="{{ old('nombre') }}"
                 class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500"
                 placeholder="Ej. Ana" />
        </div>

        <div>
          <label class="text-sm text-slate-300">Apellido Paterno</label>
          <input name="apellido_paterno" value="{{ old('apellido_paterno') }}"
                 class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500"
                 placeholder="Ej. López" />
        </div>

        <div>
          <label class="text-sm text-slate-300">Apellido Materno</label>
          <input name="apellido_materno" value="{{ old('apellido_materno') }}"
                 class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500"
                 placeholder="Ej. Hernández" />
        </div>

        <div>
          <label class="text-sm text-slate-300">Sel. Grupo</label>
          <select name="grupo_id"
                  class="mt-1 w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-3 outline-none focus:ring-2 focus:ring-sky-500">
            <option value="">-- Selecciona --</option>
            @foreach (($grupos ?? []) as $g)
              <option value="{{ $g['id'] ?? '' }}" @selected(old('grupo_id') == ($g['id'] ?? ''))>
                {{ $g['codigo'] ?? ($g['grupoCodigo'] ?? 'Grupo') }}
              </option>
            @endforeach
          </select>
          <p class="mt-2 text-xs text-slate-400">Si no aparecen grupos, registra uno primero.</p>
        </div>

        <button class="w-full rounded-2xl bg-gradient-to-r from-sky-500 to-fuchsia-500 px-5 py-3 font-semibold hover:opacity-90">
          Registrar
        </button>
      </form>
    </section>

    <aside class="rounded-3xl border border-white/10 bg-white/5 p-6">
      <h2 class="text-lg font-semibold">Accesos rápidos</h2>
      <div class="mt-4 grid gap-3">
        <a class="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 hover:bg-white/10" href="{{ route('grupos.create') }}">➕ Registrar Grupo</a>
        <a class="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 hover:bg-white/10" href="{{ route('alumnos.index') }}">📋 Ver Alumnos Registrados</a>
        <a class="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 hover:bg-white/10" href="{{ route('catalogos.index') }}">⚙️ Configurar Catálogos</a>
      </div>
    </aside>
  </div>
@endsection
