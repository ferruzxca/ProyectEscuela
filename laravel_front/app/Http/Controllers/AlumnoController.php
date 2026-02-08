<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class AlumnoController extends BaseApiController
{
    public function index()
    {
        $alumnos = [];
        try {
            $alumnos = $this->api()->get('/alumnos?includeInactivos=true')->json() ?? [];
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
            $grupos = $this->api()->get('/grupos?includeInactivos=true')->json() ?? [];
        } catch (\Throwable $e) {
            return view('alumnos.create', compact('grupos'))
                ->with('api_error', $this->apiErrorMessage($e));
        }

        return view('alumnos.create', compact('grupos'));
    }

    public function store(Request $request)
    {
        $data = $request->validate([
            'matricula'         => ['required','string','min:3','max:20'],
            'nombre'            => ['required','string','min:2','max:80'],
            'apellido_paterno'  => ['required','string','min:2','max:60'],
            'apellido_materno'  => ['required','string','min:2','max:60'],
            'grupo_id'          => ['required'],
        ], [
            'grupo_id.required' => 'Selecciona un grupo.',
        ]);

        try {
            $payload = [
                'matricula' => $data['matricula'],
                'nombre' => $data['nombre'],
                'apellidos' => trim($data['apellido_paterno'].' '.$data['apellido_materno']),
                'grupoId' => $data['grupo_id'],
            ];
            $resp = $this->api()->post('/alumnos', $payload);

            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'La API rechazó el registro. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withInput()->withErrors(['api' => $msg]);
            }

            return redirect()->route('alumnos.index')->with('ok', 'Alumno registrado.');
        } catch (\Throwable $e) {
            return back()->withInput()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function edit($id)
    {
        try {
            $alumno = $this->api()->get("/alumnos/{$id}")->json();
            if (!is_array($alumno)) {
                return redirect()->route('alumnos.index')->withErrors(['api' => 'Alumno no encontrado']);
            }
            $apellidos = trim($alumno['apellidos'] ?? '');
            $parts = preg_split('/\s+/', $apellidos, 2);
            $alumno['apellido_paterno'] = $parts[0] ?? '';
            $alumno['apellido_materno'] = $parts[1] ?? '';

            $grupos = $this->api()->get('/grupos?includeInactivos=true')->json() ?? [];
            return view('alumnos.edit', compact('alumno', 'grupos'));
        } catch (\Throwable $e) {
            return redirect()->route('alumnos.index')->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function update(Request $request, $id)
    {
        $data = $request->validate([
            'matricula'         => ['required','string','min:3','max:20'],
            'nombre'            => ['required','string','min:2','max:80'],
            'apellido_paterno'  => ['required','string','min:2','max:60'],
            'apellido_materno'  => ['required','string','min:2','max:60'],
            'grupo_id'          => ['required'],
        ], [
            'grupo_id.required' => 'Selecciona un grupo.',
        ]);

        try {
            $payload = [
                'matricula' => $data['matricula'],
                'nombre' => $data['nombre'],
                'apellidos' => trim($data['apellido_paterno'].' '.$data['apellido_materno']),
                'grupoId' => $data['grupo_id'],
            ];
            $resp = $this->api()->put("/alumnos/{$id}", $payload);
            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'No se pudo actualizar. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withInput()->withErrors(['api' => $msg]);
            }
            return redirect()->route('alumnos.index')->with('ok', 'Alumno actualizado.');
        } catch (\Throwable $e) {
            return back()->withInput()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function activate($id)
    {
        try {
            $resp = $this->api()->patch("/alumnos/{$id}/activar");
            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'No se pudo activar. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withErrors(['api' => $msg]);
            }
            return back()->with('ok', 'Alumno activado.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function destroy($id)
    {
        try {
            $resp = $this->api()->patch("/alumnos/{$id}/inactivar");
            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'No se pudo eliminar. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withErrors(['api' => $msg]);
            }
            return back()->with('ok', 'Alumno inactivado.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }
}
