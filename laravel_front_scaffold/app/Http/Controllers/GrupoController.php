<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class GrupoController extends BaseApiController
{
    public function create()
    {
        $carreras = $turnos = $grados = [];

        try {
            $carreras = $this->api()->get('/catalogos/carreras')->json() ?? [];
            $turnos   = $this->api()->get('/catalogos/turnos')->json() ?? [];
            $grados   = $this->api()->get('/catalogos/grados')->json() ?? [];
        } catch (\Throwable $e) {
            return view('grupos.create', compact('carreras','turnos','grados'))
                ->with('api_error', $this->apiErrorMessage($e));
        }

        return view('grupos.create', compact('carreras','turnos','grados'));
    }

    public function store(Request $request)
    {
        $data = $request->validate([
            'carrera_id' => ['required'],
            'turno_id'   => ['required'],
            'grado'      => ['required','string','max:10'],
            'grupo'      => ['required','string','max:30'],
        ]);

        try {
            $resp = $this->api()->post('/grupos', $data);

            if ($resp->failed()) {
                return back()->withInput()->withErrors([
                    'api' => 'La API rechazó el registro. Código: ' . $resp->status(),
                ]);
            }

            return redirect()->route('alumnos.create')->with('ok', 'Grupo registrado.');
        } catch (\Throwable $e) {
            return back()->withInput()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }
}
