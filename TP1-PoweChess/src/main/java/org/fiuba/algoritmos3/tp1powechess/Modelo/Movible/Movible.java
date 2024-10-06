package org.fiuba.algoritmos3.tp1powechess.Modelo.Movible;

import java.util.ArrayList;

public interface Movible {
    public void actualizarMovimientosPosibles(int fila, int columna);
    public ArrayList<int[]> getMovimientosPosibles();
}
