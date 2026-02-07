<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class CatalogoController extends BaseApiController
{
    public function index()
    {
        $carreras = $turnos = $grados = [];

        try {
            $carreras = $this->api()->get('/carreras')->json() ?? [];
            $turnos   = $this->api()->get('/turnos')->json() ?? [];
            $grados   = $this->api()->get('/grados')->json() ?? [];
        } catch (\Throwable $e) {
            return view('catalogos.index', compact('carreras','turnos','grados'))
                ->with('api_error', $this->apiErrorMessage($e));
        }

        return view('catalogos.index', compact('carreras','turnos','grados'));
    }

    public function storeCarrera(Request $request)
    {
        $data = $request->validate([
            'nombre' => ['required','string','min:2','max:100'],
            'sigla' => ['required','string','min:2','max:10'],
        ]);
        try {
            $resp = $this->api()->post('/carreras', $data);
            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'No se pudo registrar carrera. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withErrors(['api' => $msg]);
            }
            return back()->with('ok', 'Carrera registrada.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function storeTurno(Request $request)
    {
        $data = $request->validate([
            'nombre' => ['required','string','min:2','max:50'],
            'sigla' => ['required','string','min:1','max:5'],
        ]);
        try {
            $resp = $this->api()->post('/turnos', $data);
            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'No se pudo registrar turno. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withErrors(['api' => $msg]);
            }
            return back()->with('ok', 'Turno registrado.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function storeGrado(Request $request)
    {
        $data = $request->validate([
            'nombre' => ['required','string','min:1','max:50'],
            'numero' => ['required','integer','min:1','max:99'],
        ]);
        try {
            $resp = $this->api()->post('/grados', $data);
            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'No se pudo registrar grado. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withErrors(['api' => $msg]);
            }
            return back()->with('ok', 'Grado registrado.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function destroyCarrera($id)
    {
        try {
            $resp = $this->api()->patch("/carreras/{$id}/inactivar");
            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'No se pudo eliminar. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withErrors(['api' => $msg]);
            }
            return back()->with('ok', 'Carrera inactivada.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function activateCarrera($id)
    {
        try {
            $resp = $this->api()->patch("/carreras/{$id}/activar");
            if ($resp->failed()) {
                $detail = trim($resp->body() ?? '');
                $msg = 'No se pudo activar. Código: ' . $resp->status();
                if ($detail !== '') {
                    $msg .= ' - ' . $detail;
                }
                return back()->withErrors(['api' => $msg]);
            }
            return back()->with('ok', 'Carrera activada.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }
}
