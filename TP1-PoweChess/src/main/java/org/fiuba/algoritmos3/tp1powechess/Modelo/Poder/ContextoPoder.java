package org.fiuba.algoritmos3.tp1powechess.Modelo.Poder;

import org.fiuba.algoritmos3.tp1powechess.Modelo.Juego.Turno;
import org.fiuba.algoritmos3.tp1powechess.Modelo.Pieza.Pieza;

import java.util.Optional;

public interface ContextoPoder {
    Optional<Pieza> getPieza(int fila, int columna);
    Turno getTurno();

}
