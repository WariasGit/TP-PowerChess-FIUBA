package org.fiuba.algoritmos3.tp1powechess.Modelo.Enrocable;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Tablero.TableroCuadrado;

public interface Enrocable {
    public void enrocarSegunEnroqueDerecho(TableroCuadrado tableroCuadrado, int row);
    public void enrocarSegunEnroqueIzquierdo(TableroCuadrado tableroCuadrado,int row);
}
