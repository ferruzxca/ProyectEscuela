@extends('layouts.app')

@section('title', 'Conf. Catálogos')

@section('content')
  <div class="grid gap-6 lg:grid-cols-3">
    <!-- Carreras -->
    <section class="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-xl">
      <h1 class="text-xl font-semibold tracking-tight">Conf. Catálogos</h1>
      <p class="mt-1 text-slate-300">Carreras</p>

      <form class="mt-4 flex gap-2" method="POST" action="{{ route('catalogos.carreras.store') }}">
        @csrf
        <input name="nombre" value="{{ old('nombre') }}"
               class="w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-2 outline-none focus:ring-2 focus:ring-sky-500"
               placeholder="Ej. Sistemas" />
        <input name="sigla" value="{{ old('sigla') }}"
               class="w-28 rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-2 outline-none focus:ring-2 focus:ring-sky-500"
               placeholder="ISC" />
        <button class="rounded-2xl bg-sky-500/20 px-4 py-2 hover:bg-sky-500/30 border border-sky-400/20">Registrar</button>
      </form>

      <div class="mt-5 overflow-hidden rounded-2xl border border-white/10">
        <table class="w-full text-sm">
          <thead class="bg-white/5">
            <tr>
              <th class="px-3 py-2 text-left">Carrera</th>
              <th class="px-3 py-2 text-center">Eliminar</th>
              <th class="px-3 py-2 text-center">Activar</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-white/10">
            @forelse(($carreras ?? []) as $c)
              @php $id = $c['id'] ?? 0; @endphp
              @php $inactive = isset($c['activo']) && !$c['activo']; @endphp
              <tr class="hover:bg-white/5 {{ $inactive ? 'text-rose-300' : '' }}">
                <td class="px-3 py-2">
                  {{ $c['nombre'] ?? '—' }}
                  <span class="text-xs text-slate-400">{{ $c['sigla'] ?? '' }}</span>
                </td>
                <td class="px-3 py-2 text-center">
                  <form method="POST" action="{{ route('catalogos.carreras.destroy', $id) }}" onsubmit="return confirm('¿Eliminar carrera?')">
                    @csrf
                    @method('DELETE')
                    <button class="rounded-xl border border-rose-400/20 bg-rose-500/10 px-3 py-1 hover:bg-rose-500/20">✖</button>
                  </form>
                </td>
                <td class="px-3 py-2 text-center">
                  <form method="POST" action="{{ route('catalogos.carreras.activate', $id) }}">
                    @csrf
                    @method('PATCH')
                    <button class="rounded-xl border border-emerald-400/20 bg-emerald-500/10 px-3 py-1 hover:bg-emerald-500/20">✔</button>
                  </form>
                </td>
              </tr>
            @empty
              <tr><td colspan="3" class="px-3 py-6 text-center text-slate-400">Sin carreras</td></tr>
            @endforelse
          </tbody>
        </table>
      </div>
    </section>

    <!-- Turnos -->
    <section class="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-xl">
      <h2 class="text-lg font-semibold">Turnos</h2>
      <form class="mt-4 flex gap-2" method="POST" action="{{ route('catalogos.turnos.store') }}">
        @csrf
        <input name="nombre"
               class="w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-2 outline-none focus:ring-2 focus:ring-fuchsia-500"
               placeholder="Ej. Vespertino" />
        <input name="sigla"
               class="w-24 rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-2 outline-none focus:ring-2 focus:ring-fuchsia-500"
               placeholder="V" />
        <button class="rounded-2xl bg-fuchsia-500/20 px-4 py-2 hover:bg-fuchsia-500/30 border border-fuchsia-400/20">Registrar</button>
      </form>

      <ul class="mt-5 space-y-2">
        @forelse(($turnos ?? []) as $t)
          @php $inactive = isset($t['activo']) && !$t['activo']; @endphp
          <li class="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 {{ $inactive ? 'text-rose-300' : '' }}">
            {{ $t['nombre'] ?? '—' }}
            <span class="text-xs text-slate-400">{{ $t['sigla'] ?? '' }}</span>
          </li>
        @empty
          <li class="text-slate-400">Sin turnos</li>
        @endforelse
      </ul>
      <p class="mt-3 text-xs text-slate-400">Para eliminar/activar turnos, se agregan rutas igual que carreras cuando tu API lo defina.</p>
    </section>

    <!-- Grados -->
    <section class="rounded-3xl border border-white/10 bg-white/5 p-6 shadow-xl">
      <h2 class="text-lg font-semibold">Grados</h2>
      <form class="mt-4 flex gap-2" method="POST" action="{{ route('catalogos.grados.store') }}">
        @csrf
        <input name="nombre"
               class="w-full rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-2 outline-none focus:ring-2 focus:ring-amber-400"
               placeholder="Ej. Undécimo" />
        <input name="numero"
               class="w-20 rounded-2xl border border-white/10 bg-slate-950/40 px-4 py-2 outline-none focus:ring-2 focus:ring-amber-400"
               placeholder="11" />
        <button class="rounded-2xl bg-amber-400/20 px-4 py-2 hover:bg-amber-400/30 border border-amber-300/20">Registrar</button>
      </form>

      <ul class="mt-5 space-y-2">
        @forelse(($grados ?? []) as $g)
          @php $inactive = isset($g['activo']) && !$g['activo']; @endphp
          <li class="rounded-2xl border border-white/10 bg-white/5 px-4 py-3 {{ $inactive ? 'text-rose-300' : '' }}">
            {{ $g['nombre'] ?? '—' }}
            <span class="text-xs text-slate-400">{{ $g['numero'] ?? '' }}</span>
          </li>
        @empty
          <li class="text-slate-400">Sin grados</li>
        @endforelse
      </ul>
    </section>
  </div>
@endsection
