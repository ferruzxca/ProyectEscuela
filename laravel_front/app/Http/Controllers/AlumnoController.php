<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class AlumnoController extends BaseApiController
{
    public function index()
    {
        $alumnos = [];
        try {
            $alumnos = $this->api()->get('/alumnos')->json() ?? [];
        } catch (\Throwable $e) {
            return view('alumnos.index', compact('alumnos'))
                ->with('api_error', $this->apiErrorMessage($e));
        }

        return view('alumnos.index', compact('alumnos'));
    }

    public function create()
    {
        $grupos = [];
        try {
            $grupos = $this->api()->get('/grupos')->json() ?? [];
        } catch (\Throwable $e) {
            return view('alumnos.create', compact('grupos'))
                ->with('api_error', $this->apiErrorMessage($e));
        }

        return view('alumnos.create', compact('grupos'));
    }

    public function store(Request $request)
    {
        $data = $request->validate([
            'nombre'            => ['required','string','min:2','max:60'],
            'apellido_paterno'  => ['required','string','min:2','max:60'],
            'apellido_materno'  => ['required','string','min:2','max:60'],
            'grupo_id'          => ['required'],
        ], [
            'grupo_id.required' => 'Selecciona un grupo.',
        ]);

        try {
            $resp = $this->api()->post('/alumnos', $data);

            if ($resp->failed()) {
                return back()->withInput()->withErrors([
                    'api' => 'La API rechazó el registro. Código: ' . $resp->status(),
                ]);
            }

            return redirect()->route('alumnos.index')->with('ok', 'Alumno registrado.');
        } catch (\Throwable $e) {
            return back()->withInput()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function edit($id)
    {
        return redirect()->route('alumnos.index')->with('info', "Editar alumno #{$id} (pendiente de API).");
    }

    public function activate($id)
    {
        try {
            $resp = $this->api()->patch("/alumnos/{$id}/activar");
            if ($resp->failed()) {
                return back()->withErrors(['api' => 'No se pudo activar. Código: ' . $resp->status()]);
            }
            return back()->with('ok', 'Alumno activado.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function destroy($id)
    {
        try {
            $resp = $this->api()->delete("/alumnos/{$id}");
            if ($resp->failed()) {
                return back()->withErrors(['api' => 'No se pudo eliminar. Código: ' . $resp->status()]);
            }
            return back()->with('ok', 'Alumno eliminado.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }
}
