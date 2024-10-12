package org.fiuba.algoritmos3.tp1powechess.Modelo.Movible;

import java.util.ArrayList;

public interface Movible {
    public void setMovimientosPosibles(ArrayList<int[]> movimientosPosibles);
    public ArrayList<int[]> getMovimientosPosibles();
    public void limpiarListaMovimientosPosibles();
}
