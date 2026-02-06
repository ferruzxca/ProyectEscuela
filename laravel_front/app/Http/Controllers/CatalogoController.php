<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class CatalogoController extends BaseApiController
{
    public function index()
    {
        $carreras = $turnos = $grados = [];

        try {
            $carreras = $this->api()->get('/catalogos/carreras')->json() ?? [];
            $turnos   = $this->api()->get('/catalogos/turnos')->json() ?? [];
            $grados   = $this->api()->get('/catalogos/grados')->json() ?? [];
        } catch (\Throwable $e) {
            return view('catalogos.index', compact('carreras','turnos','grados'))
                ->with('api_error', $this->apiErrorMessage($e));
        }

        return view('catalogos.index', compact('carreras','turnos','grados'));
    }

    public function storeCarrera(Request $request)
    {
        $data = $request->validate(['nombre' => ['required','string','min:2','max:80']]);
        try {
            $resp = $this->api()->post('/catalogos/carreras', $data);
            if ($resp->failed()) return back()->withErrors(['api' => 'No se pudo registrar carrera. Código: '.$resp->status()]);
            return back()->with('ok', 'Carrera registrada.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function storeTurno(Request $request)
    {
        $data = $request->validate(['nombre' => ['required','string','min:2','max:40']]);
        try {
            $resp = $this->api()->post('/catalogos/turnos', $data);
            if ($resp->failed()) return back()->withErrors(['api' => 'No se pudo registrar turno. Código: '.$resp->status()]);
            return back()->with('ok', 'Turno registrado.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function storeGrado(Request $request)
    {
        $data = $request->validate(['nombre' => ['required','string','min:1','max:10']]);
        try {
            $resp = $this->api()->post('/catalogos/grados', $data);
            if ($resp->failed()) return back()->withErrors(['api' => 'No se pudo registrar grado. Código: '.$resp->status()]);
            return back()->with('ok', 'Grado registrado.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function destroyCarrera($id)
    {
        try {
            $resp = $this->api()->delete("/catalogos/carreras/{$id}");
            if ($resp->failed()) return back()->withErrors(['api' => 'No se pudo eliminar. Código: '.$resp->status()]);
            return back()->with('ok', 'Carrera eliminada.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }

    public function activateCarrera($id)
    {
        try {
            $resp = $this->api()->patch("/catalogos/carreras/{$id}/activar");
            if ($resp->failed()) return back()->withErrors(['api' => 'No se pudo activar. Código: '.$resp->status()]);
            return back()->with('ok', 'Carrera activada.');
        } catch (\Throwable $e) {
            return back()->withErrors(['api' => $this->apiErrorMessage($e)]);
        }
    }
}
