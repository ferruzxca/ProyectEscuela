@extends('layouts.app')

@section('title', 'Alumnos Registrados')

@section('content')
  <section class="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-xl">
    <div class="flex flex-col gap-3 sm:flex-row sm:items-end sm:justify-between">
      <div>
        <h1 class="text-2xl font-semibold tracking-tight">Alumnos Registrados</h1>
        <p class="mt-1 text-slate-300">Listado general (desde la API de Spring Boot).</p>
      </div>

      <div class="flex gap-2">
        <a href="{{ route('alumnos.create') }}" class="rounded-2xl border border-white/10 bg-white/5 px-4 py-2 hover:bg-white/10">➕ Alumno</a>
        <a href="{{ route('grupos.create') }}" class="rounded-2xl border border-white/10 bg-white/5 px-4 py-2 hover:bg-white/10">➕ Grupo</a>
      </div>
    </div>

    <div class="mt-6 overflow-x-auto rounded-2xl border border-white/10">
      <table class="min-w-full text-sm">
        <thead class="bg-white/5 text-slate-200">
          <tr>
            <th class="px-4 py-3 text-left">#</th>
            <th class="px-4 py-3 text-left">Alumno</th>
            <th class="px-4 py-3 text-left">Grupo</th>
            <th class="px-4 py-3 text-left">Acciones</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-white/10">
          @forelse(($alumnos ?? []) as $i => $a)
            @php
              $id = $a['id'] ?? null;
              $full = trim(($a['nombre'] ?? '').' '.($a['apellido_paterno'] ?? '').' '.($a['apellido_materno'] ?? ''));
              $grupo = $a['grupo'] ?? $a['grupo_nombre'] ?? $a['grupoClave'] ?? '-';
            @endphp
            <tr class="hover:bg-white/5">
              <td class="px-4 py-3 text-slate-300">{{ $i + 1 }}</td>
              <td class="px-4 py-3 font-medium">{{ $full ?: 'Sin nombre' }}</td>
              <td class="px-4 py-3 text-slate-200">{{ $grupo }}</td>
              <td class="px-4 py-3">
                <div class="flex gap-2">
                  <a href="{{ route('alumnos.edit', $id ?? 0) }}"
                     class="rounded-xl border border-white/10 bg-white/5 px-3 py-1 hover:bg-white/10"
                     title="Editar (placeholder)">✏️</a>

                  <form method="POST" action="{{ route('alumnos.activate', $id ?? 0) }}">
                    @csrf
                    @method('PATCH')
                    <button class="rounded-xl border border-emerald-400/20 bg-emerald-500/10 px-3 py-1 hover:bg-emerald-500/20"
                            title="Activar">✅</button>
                  </form>

                  <form method="POST" action="{{ route('alumnos.destroy', $id ?? 0) }}"
                        onsubmit="return confirm('¿Eliminar alumno?')">
                    @csrf
                    @method('DELETE')
                    <button class="rounded-xl border border-rose-400/20 bg-rose-500/10 px-3 py-1 hover:bg-rose-500/20"
                            title="Eliminar">🗑️</button>
                  </form>
                </div>
              </td>
            </tr>
          @empty
            <tr>
              <td colspan="4" class="px-4 py-8 text-center text-slate-400">
                No hay alumnos para mostrar. (O la API aún no devuelve datos)
              </td>
            </tr>
          @endforelse
        </tbody>
      </table>
    </div>
  </section>
@endsection
