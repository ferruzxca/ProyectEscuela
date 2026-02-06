<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>@yield('title', 'Sistema Escolar')</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <script defer src="https://unpkg.com/alpinejs@3.x.x/dist/cdn.min.js"></script>
</head>
<body class="min-h-screen bg-slate-950 text-slate-100">
  <div class="absolute inset-0 -z-10 bg-[radial-gradient(circle_at_top,rgba(59,130,246,0.25),transparent_45%),radial-gradient(circle_at_bottom,rgba(236,72,153,0.18),transparent_45%)]"></div>

  <header class="sticky top-0 z-50 border-b border-white/10 bg-slate-950/70 backdrop-blur">
    <div class="mx-auto max-w-6xl px-4 py-3 flex items-center justify-between">
      <a href="{{ route('home') }}" class="flex items-center gap-2 font-semibold tracking-wide">
        <span class="inline-flex h-9 w-9 items-center justify-center rounded-xl bg-white/10 border border-white/10">SA</span>
        <span>Sistema de Alumnos</span>
      </a>

      <nav class="flex items-center gap-3" x-data="{open:false}">
        <div class="relative">
          <button @click="open = !open" class="inline-flex items-center gap-2 rounded-xl border border-white/10 bg-white/5 px-4 py-2 hover:bg-white/10 transition">
            Menú
            <svg class="h-4 w-4 opacity-80" viewBox="0 0 20 20" fill="currentColor"><path fill-rule="evenodd" d="M5.23 7.21a.75.75 0 0 1 1.06.02L10 10.94l3.71-3.71a.75.75 0 1 1 1.06 1.06l-4.24 4.24a.75.75 0 0 1-1.06 0L5.21 8.29a.75.75 0 0 1 .02-1.08z" clip-rule="evenodd"/></svg>
          </button>

          <div x-show="open" @click.outside="open=false" x-transition
               class="absolute right-0 mt-2 w-64 rounded-2xl border border-white/10 bg-slate-950/90 backdrop-blur p-2 shadow-xl">
            <a class="block rounded-xl px-3 py-2 hover:bg-white/10" href="{{ route('alumnos.create') }}">Registro de Alumnos</a>
            <a class="block rounded-xl px-3 py-2 hover:bg-white/10" href="{{ route('grupos.create') }}">Registro de Grupos</a>
            <a class="block rounded-xl px-3 py-2 hover:bg-white/10" href="{{ route('alumnos.index') }}">Alumnos Registrados</a>
            <a class="block rounded-xl px-3 py-2 hover:bg-white/10" href="{{ route('catalogos.index') }}">Conf. Catálogos</a>
          </div>
        </div>
      </nav>
    </div>
  </header>

  <main class="mx-auto max-w-6xl px-4 py-8">
    @if (session('ok'))
      <div class="mb-5 rounded-2xl border border-emerald-400/20 bg-emerald-500/10 px-4 py-3 text-emerald-200">
        ✅ {{ session('ok') }}
      </div>
    @endif

    @if (session('info'))
      <div class="mb-5 rounded-2xl border border-sky-400/20 bg-sky-500/10 px-4 py-3 text-sky-200">
        ℹ️ {{ session('info') }}
      </div>
    @endif

    @if (!empty($api_error))
      <div class="mb-5 rounded-2xl border border-rose-400/20 bg-rose-500/10 px-4 py-3 text-rose-200">
        ⚠️ {{ $api_error }}
      </div>
    @endif

    @if ($errors->any())
      <div class="mb-5 rounded-2xl border border-rose-400/20 bg-rose-500/10 px-4 py-3 text-rose-200">
        <div class="font-semibold mb-1">Corrige lo siguiente:</div>
        <ul class="list-disc pl-6 space-y-1">
          @foreach ($errors->all() as $e)
            <li>{{ $e }}</li>
          @endforeach
        </ul>
      </div>
    @endif

    @yield('content')
  </main>

  <footer class="border-t border-white/10 py-6 text-center text-sm text-slate-400">
    Front Laravel • Consumiendo API Spring Boot
  </footer>
</body>
</html>
