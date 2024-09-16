package org.fiuba.algoritmos3.tp1powechess.Model;

public interface EstadoCasillero {
    boolean estaOcupado();

    default int getNumeroDeAmenazasBlanco() {
        return 0;
    }

    default int getNumeroDeAmenazasNegro() {
        return 0;
    }
}