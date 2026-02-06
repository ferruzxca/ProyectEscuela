<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class GrupoController extends BaseApiController
{
    public function create()
    {
        $carreras = $turnos = $grados = [];

        try {
            $carreras = $this->api()->get('/carreras')->json() ?? [];
            $turnos   = $this->api()->get('/turnos')->json() ?? [];
            $grados   = $this->api()->get('/grados')->json() ?? [];
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
            'grado_id'   => ['required'],
        ]);

        try {
            $payload = [
                'carreraId' => $data['carrera_id'],
                'turnoId' => $data['turno_id'],
                'gradoId' => $data['grado_id'],
            ];
            $resp = $this->api()->post('/grupos', $payload);

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
